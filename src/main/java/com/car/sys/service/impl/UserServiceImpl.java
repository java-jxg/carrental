package com.car.sys.service.impl;

import com.car.sys.constast.SysConstast;
import com.car.sys.domain.*;
import com.car.sys.mapper.RoleMapper;
import com.car.sys.mapper.RoleUserMapper;
import com.car.sys.mapper.UserMapper;
import com.car.sys.service.UserService;
import com.car.sys.utils.DataGridView;
import com.car.sys.vo.UserVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleUserMapper roleUserMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public User login(UserVo userVo) {
        String pwd = DigestUtils.md5DigestAsHex(userVo.getPwd().getBytes());
        userVo.setPwd(pwd);
        UserExample example = new UserExample();
        example.createCriteria().andLoginnameEqualTo(userVo.getLoginname()).andPwdEqualTo(userVo.getPwd());

        List<User> users = userMapper.selectByExample(example);
        if(users.size()>=1){
            return users.get(0);
        }else{
            return null;
        }

    }

    @Override
    public DataGridView queryAllUser(UserVo uservo) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if(uservo.getLoginname()!=null){
            criteria.andLoginnameLike("%"+uservo.getLoginname()+"%");
        }
        if(uservo.getRealname()!=null){
            criteria.andRealnameLike("%"+uservo.getRealname()+"%");
        }
        if(uservo.getAddress()!=null){
            criteria.andAddressLike("%"+uservo.getAddress()+"%");
        }
        if(uservo.getPhone()!=null){
            criteria.andPhoneLike("%"+uservo.getPhone()+"%");
        }
        if(uservo.getIdentity()!=null){
            criteria.andIdentityLike("%"+uservo.getIdentity()+"%");
        }
        if(uservo.getSex()!=null){
            criteria.andSexEqualTo(uservo.getSex());
        }
        criteria.andTypeNotEqualTo(1);
        Page<Object> page = PageHelper.startPage(uservo.getPage(),uservo.getLimit());
        List<User> users = userMapper.selectByExample(example);
        return new DataGridView(page.getTotal(),users);
    }

    @Override
    public void addUser(UserVo uservo) {
        userMapper.insert(uservo);
    }

    @Override
    public void updateUser(UserVo uservo) {
        userMapper.updateByPrimaryKey(uservo);
    }

    @Override
    public void deleteBatchRole(Integer[] ids) {
        for (Integer id:ids){
            deleteUser(id);
        }
    }

    @Override
    public void deleteUser(Integer userid) {
        userMapper.deleteByPrimaryKey(userid);
        RoleUserExample example = new RoleUserExample();
        example.createCriteria().andUidEqualTo(userid);
        roleUserMapper.deleteByExample(example);
    }

    @Override
    public void resetUserPwd(Integer userid) {
        String pwd = DigestUtils.md5DigestAsHex(SysConstast.USER_DEFAULT_PWD.getBytes());
        UserExample example = new UserExample();
        example.createCriteria().andPwdEqualTo(pwd);
        User user = userMapper.selectByPrimaryKey(userid);
        user.setPwd(pwd);
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public DataGridView initUserRole(UserVo uservo) {
        //查询可用角色
        RoleExample example = new RoleExample();
        example.createCriteria().andAvailableEqualTo(SysConstast.AVAILABLE_TRUE);
        List<Role> roles = roleMapper.selectByExample(example);
        //查询用户已有角色
        RoleUserExample roleUserExample = new RoleUserExample();
        roleUserExample.createCriteria().andUidEqualTo(uservo.getUserid());
        List<RoleUserKey> roleUserKeys = roleUserMapper.selectByExample(roleUserExample);
        List<Map<String,Object>> data = new ArrayList<>();
        for(Role r : roles){
            Boolean LAY_CHECKED=false;
            for(RoleUserKey ru:roleUserKeys){
                if(r.getRoleid()==ru.getRid()){
                    LAY_CHECKED=true;
                }
            }
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("roleid",r.getRoleid());
            map.put("rolename",r.getRolename());
            map.put("roledesc",r.getRoledesc());
            map.put("LAY_CHECKED",LAY_CHECKED);
            data.add(map);
        }
        return new DataGridView(data);
    }

    @Override
    public void saveUserRole(UserVo userVo) {
        //删除RoleUser表中的数据
        RoleUserExample example = new RoleUserExample();
        example.createCriteria().andUidEqualTo(userVo.getUserid());
        roleUserMapper.deleteByExample(example);
        //添加roleUser表中的数据
        for(Integer id : userVo.getIds()){
            RoleUserKey ru = new RoleUserKey();
            ru.setRid(id);
            ru.setUid(userVo.getUserid());
            roleUserMapper.insert(ru);
        }
    }
}
