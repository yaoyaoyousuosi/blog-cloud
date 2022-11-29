package org.cloud.blog.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.cloud.blog.common.exception.BlogException;
import org.cloud.blog.common.utils.JwtUtils;
import org.cloud.blog.common.utils.RsaUtil;
import org.cloud.blog.ucenter.domain.Admin;
import org.cloud.blog.ucenter.domain.SysUser;
import org.cloud.blog.ucenter.mapper.SysUserMapper;
import org.cloud.blog.ucenter.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whj
 * @since 2022-11-28
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource SysUserMapper sysUserMapper;
    @Resource RabbitTemplate rabbitTemplate;
    @Override
    public String userLogin(SysUser user) throws BlogException {
        String username = user.getAccount();
        String password = user.getPassword();
        if(StringUtils.isEmpty(username)){
            throw new BlogException("请输入用户名");
        }
        LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysUser::getAccount, username);
        SysUser authUser = sysUserMapper.selectOne(wrapper);
        if(null == authUser){
            throw new BlogException("该用户不存在，请先注册");
        }
        String decodePwd = RsaUtil.rsaDecode(authUser.getPassword());
        if(!decodePwd.equals(password)){
            throw new BlogException("密码输入错误，请重试");
        }
        return JwtUtils.createJwtToken(authUser.getId(), authUser.getNickname());
    }

    @Override
    public String doRegister(SysUser sysUser) throws BlogException {
        String account = sysUser.getAccount();
        String password = sysUser.getPassword();
        String nickname = sysUser.getNickname();
        String phone = sysUser.getPhone();
        if(StringUtils.isEmpty(account) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(phone)){
            throw new BlogException("请填入必填项");
        }
        long timeMillis = System.currentTimeMillis();
        String encode = RsaUtil.rsaEncode(password);
        SysUser user = new SysUser(null, account, false, null, timeMillis,
                false, null, timeMillis, nickname, encode, "1", phone);
        int count = sysUserMapper.insert(user);
        String token = null;
        if(count > 0){
            String id = user.getId();
            token = JwtUtils.createJwtToken(id, account);
            String version = UUID.randomUUID().toString();
            send(nickname, phone, version, password);
        }
        return token;
    }

    private void send(String nickname, String phone, String version, String password){
        HashMap<String, String> message = new HashMap<>();
        message.put("nickname", nickname);
        message.put("phone", phone);
        message.put("version", version);
        message.put("password", password);
        rabbitTemplate.convertAndSend("phoneExchange", "phone", message);
    }
}
