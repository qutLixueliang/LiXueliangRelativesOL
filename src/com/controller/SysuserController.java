package com.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.entity.Ftype;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.entity.Sysuser;
import com.server.FtypeServer;
import com.server.SysuserServer;
import com.util.PageBean;

import net.sf.json.JSONObject;

@Controller
public class SysuserController {
	@Resource
	private SysuserServer userService;
	@Resource
	private FtypeServer ftypeService;

    /* 通用方法 */
    public void typeListAll(ModelMap map, String btype) {
        Map<String, Object> tttmap = new HashMap<>();
        Map<String, Object> umap = new HashMap<>();
        tttmap.put("btype", btype);
        List<Ftype> tttlist = ftypeService.getAll(tttmap);
        if (btype.equals("板块")) {
            map.put("klist", tttlist);
        } else {
            map.put("tlist", tttlist);
        }
        umap.put("utype","用户");
        map.put("ulist",userService.getAll(umap));
        map.put("ualist",userService.getAll(null));
    }

	/**
	 * ===前台信息管理===
	 * 

	 * @return
	 */

	// 前台登录
	@RequestMapping("login.do")
	public String checkAdminLogin(Sysuser user, HttpSession session) {
		Map<String, Object> u = new HashMap<String, Object>();
		System.out.println("name===" + user.getUname());
		u.put("uname", user.getUname());
		u.put("pwd", user.getPwd());
		u.put("utype", user.getUtype());
		user = userService.adminLogin(u);
		if (user != null) {
			session.setAttribute("user", user);
			System.out.println("user=" + user);
			session.removeAttribute("suc");
			return "redirect:index.do";
		} else {
			session.setAttribute("suc", "登录失败！用户名或密码错误！");
			return "login";
		}

	}

    /*用户*/
    @RequestMapping("userListFore.do")
    public String userListFore(ModelMap map) {
        Map<String,Object> uMap = new HashMap<>();
        uMap.put("utype","用户");
        map.put("list",userService.getAll(uMap));
        typeListAll(map, "板块");
        typeListAll(map, "分类");
        return "user_list";
    }


    @RequestMapping("searchUser.do")
    public String searchUser(@RequestParam(value = "page", required = false) String page, HttpSession session,
                           ModelMap map,Sysuser user) {
        Map<String, Object> pmap = new HashMap<>();
        if (user.getTname()!=null&&!user.getTname().equals("")){
            pmap.put("tname",user.getTname());
        }
        pmap.put("utype","用户");
        map.put("list", userService.getAll(pmap));
        typeListAll(map, "板块");
        typeListAll(map, "分类");
        return "user_list";
    }

    /*详细信息*/
    @RequestMapping("showUserx.do")
    public String showUserx(ModelMap map,int id) {
        map.put("user",userService.getById(id));
        typeListAll(map, "板块");
        typeListAll(map, "分类");
        return "userx";
    }


	// 
	@RequestMapping("forPwd.do")
	public String forPwd(Sysuser user, ModelMap map) {
		Map<String, Object> umap = new HashMap<String, Object>();
		umap.put("uname", user.getUname());
		Sysuser u = userService.checkUname(umap);
		if (u == null || u.equals("")) {
			return "error_uname";
		} else {
			map.put("user", u);
			return "fore_losspwd";
		}

	}

	// 处理修改个人信息
	@RequestMapping("showInfo.do")
	public String showInfo(HttpSession session, ModelMap map) {
		Sysuser u = (Sysuser) session.getAttribute("user");
		if (u == null) {
			return "login";
		} else {
			map.put("user", userService.getById(u.getId()));
			return "showUserinfo";
		}
	}

	// 处理修改个人信息
	@RequestMapping("addShowInfo.do")
	public String addShowInfo(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request,Sysuser user, HttpSession session,String img) {
		img=fileUpload(file, request, img);
		user.setImg(img);
		userService.update(user);
		return "success";
	}

	// 前台注销登录
	@RequestMapping("loginout.do")
	public String loginOut(HttpSession session) {
		session.removeAttribute("user");
		session.removeAttribute("p");
		session.setAttribute("suc", "");
		session.removeAttribute("suc");
		return "login";
	}


	// 验证用户名是否存在
	@RequestMapping("checkUname.do")
	public void checkReg(Sysuser user, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();
		map.put("uname", user.getUname());
		System.out.println("uname===" + user.getUname());
		System.out.println("uname222===" + userService.checkUname(map));
		JSONObject obj = new JSONObject();
		if (userService.checkUname(map) != null) {
			System.out.println("uname233333333333===");
			obj.put("info", "ng");
		} else {
			System.out.println("uname255555555555555===");
			obj.put("info", "用户名可以用！");

		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(obj);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	@RequestMapping("checkPass.do")
	public void checkPass(Sysuser user, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();
		map.put("uname", user.getUname());
		System.out.println("uname===" + user.getUname());
		System.out.println("uname222===" + userService.checkUname(map));
		JSONObject obj = new JSONObject();
		if (userService.checkUname(map) != null) {
			obj.put("info", userService.checkUname(map).getPwd());
		} else {
			obj.put("info", "ng");

		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(obj);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	@RequestMapping("reg.do")
	public String addReg(@RequestParam(value = "file", required = false) MultipartFile file,
	HttpServletRequest request,Sysuser user, HttpSession session,String img) {
		img = fileUpload(file, request, img);
		user.setImg(img);
		user.setIsdel("1");
		if(user.getUtype().equals("志愿者")){
		    user.setVtype("空闲中");
        }
		Timestamp time = new Timestamp(System.currentTimeMillis());
		user.setPubtime(time.toString().substring(0, 19));
		userService.add(user);
		return "login";
	}

	/**
	 * ===后台管理===
	 * 
	 * @param user
	 * @param session
	 * @return
	 */

	// 后台登录
	@RequestMapping("admin/alogin.do")
	public String checkLogin(Sysuser user, HttpSession session) {
		Map<String, Object> u = new HashMap<String, Object>();
		System.out.println("name===" + user.getUname());
		System.out.println("pwd===" + user.getPwd());
		u.put("uname", user.getUname());
		u.put("utype", user.getUtype());
		u.put("pwd", user.getPwd());
		user = userService.adminLogin(u);
		if (user != null) {
			session.setAttribute("auser", user);
			System.out.println("auser=" + user);
			return "admin/index2";
		} else {
			return "admin/login";
		}
	}

	// 后台注销登录
	@RequestMapping("admin/loginout.do")
	public String adminLoginOut(HttpSession session) {
		session.removeAttribute("auser");
		return "admin/login";
	}

	// 验证用户名是否存在
	@RequestMapping("admin/checkUname.do")
	public void checkUname(Sysuser user, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uname", user.getUname());
		System.out.println("uname===" + user.getUname());
		System.out.println("uname222===" + userService.checkUname(map));
		JSONObject obj = new JSONObject();
		if (userService.checkUname(map) != null) {

			obj.put("info", "ng");
		} else {
			obj.put("info", "用户名可以用！");

		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(obj);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	@RequestMapping("admin/showUserInfo.do")
	public String showUserInfo(ModelMap map, HttpSession session) {
		if (session.getAttribute("auser") == null) {
			return "admin/login";
		}
		Sysuser u = (Sysuser) session.getAttribute("auser");
		map.put("user", userService.getById(u.getId()));
		return "admin/update_user_persion";
	}

	@RequestMapping("admin/updatePersionUser.do")
	public String updateUserInfo(ModelMap map, @RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, Sysuser user, HttpSession session, String img) {
		img = fileUpload(file, request, img);
		if (img != null) {
			user.setImg(img);
		}
		userService.update(user);
		map.put("user", userService.getById(user.getId()));
		session.setAttribute("suc", "cc");
		return "redirect:showUserInfo.do";
	}

	// 处理更新用户的信息
	@RequestMapping("admin/doUpdateUser.do")
	public String doUpdateUser(ModelMap map, int id) {
		System.out.println("id==" + id);
		map.put("user", userService.getById(id));
		return "admin/user_update";
	}

	// 更新用户的信息
	@RequestMapping("admin/updateUser.do")
	public String updateUser(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, Sysuser user, HttpSession session, String img) {
		img = fileUpload(file, request, img);
		if (img != null) {
			user.setImg(img);
		}
		Timestamp time = new Timestamp(System.currentTimeMillis());
		user.setPubtime(time.toString().substring(0, 19));
		userService.update(user);
		return "admin/success";
	}

	@RequestMapping("admin/addUser.do")
	public String addUser(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, Sysuser user, HttpSession session, String img) {
		img = fileUpload(file, request, img);
		user.setImg(img);
		user.setIsdel("1");
		Timestamp time = new Timestamp(System.currentTimeMillis());
		user.setPubtime(time.toString().substring(0, 19));
		userService.add(user);
		return "redirect:userList.do";
	}

	// 查询所有用户的信息
	@RequestMapping("admin/userList.do")
	public String userList(@RequestParam(value = "page", required = false) String page, HttpSession session,
			ModelMap map) {
		if (page == null || page.equals("")) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), PageBean.PAGESIZE);
		Map<String, Object> pmap = new HashMap<String, Object>();
		pmap.put("pageno", pageBean.getStart());
		pmap.put("pageSize", pageBean.getPageSize());
		Map<String, Object> cmap = new HashMap<>();
		pmap.put("utype", null);
		cmap.put("utype", null);
		int total = userService.getCount(cmap);
		pageBean.setTotal(total);
		// List<Sysuser> list = userService.getByPage(pmap);
		map.put("page", pageBean);
		map.put("list", userService.getAll(null));
		session.setAttribute("p", 1);
		return "admin/user_list";
	}

	// 模糊查询并分页
	@RequestMapping("admin/userListQuery.do")
	public String useListQuery(@RequestParam(value = "page", required = false) String page, HttpSession session,
			ModelMap map, Sysuser user) {
		if (page == null || page.equals("")) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), PageBean.PAGESIZE);
		Map<String, Object> pmap = new HashMap<String, Object>();
		pmap.put("pageno", pageBean.getStart());
		pmap.put("pageSize", pageBean.getPageSize());
		Map<String, Object> cmap = new HashMap<>();
		if (user.getUtype() != null && !user.getUtype().equals("")) {
			pmap.put("utype", user.getUtype());
			cmap.put("utype", user.getUtype());
		}
		if (user.getUname() != null && !user.getUname().equals("")) {
			pmap.put("uname", user.getUname());
			cmap.put("uname", user.getUname());
		}
		if (user.getTname() != null && !user.getTname().equals("")) {
			pmap.put("tname", user.getTname());
			cmap.put("tname", user.getTname());
		}
		int total = userService.getCount(cmap);
		pageBean.setTotal(total);
		// List<Sysuser> list = userService.getByPage(pmap);
		List<Sysuser> list = userService.getAll(pmap);
		map.put("page", pageBean);
		map.put("list", list);
		session.setAttribute("p", 2);
		return "admin/user_list";
	}

	@RequestMapping("admin/deleteUser.do")
	public String deleteUser(int id) {
		userService.delete(id);
		return "redirect:userList.do";
	}

	// 文件上传
	public String fileUpload(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, String img) {
		String path = request.getSession().getServletContext().getRealPath("upload");
		System.out.println("path===" + path);
		System.out.println("file===" + file);
		String fileName = file.getOriginalFilename();
		System.out.println("fileName===" + fileName);
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		try {
			file.transferTo(targetFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String pa = request.getContextPath() + "/upload/" + fileName;
		System.out.println("path===" + pa);
		if (fileName != null && !fileName.equals("")) {
			img = fileName;
		} else {
			img = "zanwu.jpg";
		}
		return img;
	}

}
