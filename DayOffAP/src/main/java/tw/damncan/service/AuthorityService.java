package tw.damncan.service;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;

import tw.damncan.dao.DepartmentDAO;
import tw.damncan.dao.UserDAO;
import tw.damncan.model.Department;
import tw.damncan.model.LoginUser;
import tw.damncan.model.User;

public class AuthorityService {
	ApplicationContext context;
	
	public AuthorityService(ApplicationContext context){
		this.context = context;
	}

    public LoginUser login(String reqAccount, String reqPswd) throws InstantiationException, IllegalAccessException {
		SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest256();
		String pswd = Hex.toHexString(digestSHA3.digest(reqPswd.getBytes()));
		String account = reqAccount;
		
		UserDAO userDAO = (UserDAO) context.getBean("userDAO");
		User user = (User) userDAO.findByColumn(User.class, "account", account);
		
        if(pswd.equals(user.getPswd())){
        	// dig department information
        	DepartmentDAO departmentDAO = (DepartmentDAO) context.getBean("departmentDAO");
        	Department department = (Department) departmentDAO.findByColumn(Department.class, "id" ,user.getDepID());
    		
    		LoginUser loginUser = new LoginUser();
    		BeanUtils.copyProperties(user, loginUser);
    		loginUser.setDepName(department.getName());
    		loginUser.setDepNumber(department.getNumber());
    		
    	    return loginUser;
		}else{
			return null;
		}
    }
}