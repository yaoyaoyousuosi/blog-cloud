package org.cloud.blog.ucenter.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.cloud.blog.common.exception.BlogException;
import org.cloud.blog.common.utils.JwtUtils;
import org.cloud.blog.common.utils.Response;
import org.cloud.blog.common.utils.RsaUtil;
import org.cloud.blog.ucenter.client.ProducerClient;
import org.cloud.blog.ucenter.domain.SysUser;
import org.cloud.blog.ucenter.mapper.SysUserMapper;
import org.cloud.blog.ucenter.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;

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

    @Resource private SysUserMapper sysUserMapper;
    @Resource private ProducerClient producerClient;

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
        if(authUser.getDeleted()){
            throw new BlogException("您目前处于禁用状态，请联系管理员");
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
        Boolean bool = checkAccount(account);
        String token = null;
        if(bool){
            long timeMillis = System.currentTimeMillis();
            String encode = RsaUtil.rsaEncode(password);
            SysUser user = new SysUser(null, account, false, null, timeMillis,
                    false, null, timeMillis, nickname, encode, "1", phone);
            int count = sysUserMapper.insert(user);
            if(count > 0){
                String id = user.getId();
                token = JwtUtils.createJwtToken(id, account);
                send(phone, account, password);
            }
        }
        return token;
    }

    private void send(String phone, String account, String password ){
        String version = String.valueOf(IdUtil.getSnowflakeNextId());
        HashMap<String, String> payload = new HashMap<>();
        payload.put("phone", phone);
        payload.put("account", account);
        payload.put("password", password);
        payload.put("version", version);
        // TODO: 2022/12/1 客户端降级，可做日志记录。
        producerClient.send(payload);
    }


    private Boolean checkAccount(String account){
        LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysUser::getAccount, account);
        SysUser sysUser = baseMapper.selectOne(wrapper);
        if(null == sysUser){
            return true;
        }
        return false;
    }

    @Override
    public SysUser getCurrentUser(String token) {
        String id = JwtUtils.getUserId(token);
        SysUser sysUser = baseMapper.selectById(id);
        sysUser.setPassword(null);
        sysUser.setDeleted(null);
        sysUser.setPhone(null);
        return sysUser;
    }

    @Override
    public void updateUser(SysUser user, String userId) {
        user.setId(userId);
        if (StringUtils.isEmpty(user.getNickname())) {
            user.setNickname(null);
        }
        if (StringUtils.isEmpty(user.getEmail())) {
            user.setEmail(null);
        }
        if (StringUtils.isEmpty(user.getAvatar())) {
            user.setAvatar(null);
        }
        baseMapper.updateById(user);
    }
}
