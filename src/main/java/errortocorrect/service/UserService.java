package errortocorrect.service;

import errortocorrect.dto.LoginDto;
import errortocorrect.dto.RegDto;
import errortocorrect.entity.User;
import errortocorrect.exception.PassWordNotEqualException;
import errortocorrect.exception.UserExistException;
import errortocorrect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public List<User> userList() {
        return userRepository.findAll();
    }

    public User login(LoginDto loginDto){
        User user = userRepository.findByUserName(loginDto.getUserName());
       if(null != user)
       {
           //密码验证
           if(user.getPassword().equals(loginDto.getPassword())){
               return user;
           }
           else {
               return null;
           }
       }
       else {
           return null;
       }
    }

    public void register(RegDto regDto) throws UserExistException, PassWordNotEqualException {
        User user = userRepository.findByUserName(regDto.getUserName());
        User snoUser = userRepository.findBySno(Long.valueOf(regDto.getSno()));
        if(snoUser != null)
        {
            throw new UserExistException("学号已存在");
        }
        if(user != null)
        {
            throw new UserExistException("用户已存在");
        }
        if(!regDto.getPassWord().equals(regDto.getCheckPass()))
        {
            throw new PassWordNotEqualException("两次密码不匹配");
        }
        User newUser = new User();
        newUser.setType(0);//默认学生
        newUser.setUserName(regDto.getUserName());
        newUser.setPassword(regDto.getPassWord());
        newUser.setSno(Long.valueOf(regDto.getSno()));
        userRepository.save(newUser);
    }
}
