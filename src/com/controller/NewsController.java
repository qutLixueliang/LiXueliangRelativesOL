package com.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.entity.BbsWithBLOBs;
import com.entity.Ftype;
import com.entity.Goods;
import com.entity.News;
import com.entity.Sysuser;
import com.server.BbsServer;
import com.server.FtypeServer;
import com.server.GoodsServer;
import com.server.NewsServer;
import com.server.SysuserServer;
import com.util.PageBean;

@Controller
public class NewsController {
    @Resource
    private NewsServer newsService;
    @Resource
    private SysuserServer userService;
    @Resource
    private FtypeServer ftypeService;
    @Resource
    private GoodsServer goodsService;
    @Resource
    private BbsServer bbsService;

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
        umap.put("utype", "用户");
        map.put("ulist", userService.getAll(umap));
        map.put("ualist", userService.getAll(null));
    }

    /* 浏览记录通用方法 */
    public void recordAdd(HttpSession session, int id, Ftype ftype) {
       /* Sysuser user = (Sysuser) session.getAttribute("user");
        if (user != null) {
            Map<String, Object> rmap = new HashMap<>();
            rmap.put("uid", user.getId());
            rmap.put("fid", id);
            int count = recordService.getCount(rmap);
            if (count > 0) {
                Timestamp etime = new Timestamp(System.currentTimeMillis());
                Record rec = recordService.getAll(rmap).get(0);
                rec.setSum(rec.getSum() + 1);
                rec.setEtime(etime.toString().substring(0, 19));
                recordService.update(rec);
            } else {
                Record record = new Record();
                if (ftype.getBtype().equals("板块")) {
                    record.setGtype("论坛");
                } else {
                    record.setGtype("水果");
                }
                record.setGtype(ftype.getBtype());
                record.setFid(id);
                record.setUid(user.getId());
                record.setIsdel("1");
                record.setTid(ftype.getId());
                Timestamp pubtime = new Timestamp(System.currentTimeMillis());
                record.setPubtime(pubtime.toString().substring(0, 19));
                record.setSum(1);
                recordService.add(record);
            }

        }*/
    }

    // 首页显示内容
    @RequestMapping("index.do")
    public String showIndex(ModelMap map) {
        Map<String, Object> pmap = new HashMap<>();
        Map<String, Object> pmap2 = new HashMap<>();
        Map<String, Object> jzmap = new HashMap<>();
        Map<String, Object> spmap = new HashMap<>();
        Map<String, Object> umap = new HashMap<>();

        pmap2.put("pageno", 0);
        pmap2.put("pageSize", 4);
        pmap2.put("gtype", "新闻");

        spmap.put("pageno", 0);
        spmap.put("pageSize", 4);
        spmap.put("gtype", "指南");

        jzmap.put("pageno", 0);
        jzmap.put("pageSize", 4);

        umap.put("pageno", 0);
        umap.put("pageSize", 4);
        umap.put("utype", "用户");

        List<News> list = newsService.getByPage(pmap);

        List<News> list2 = newsService.getByPage(pmap2);
        List<News> splist = newsService.getByPage(spmap);

        List<Sysuser> ulist = userService.getByPage(umap);

        List<Goods> glist = goodsService.getTop(jzmap);

        Map<String, Object> kmap2 = new HashMap<>();
        kmap2.put("btype", "板块");
        kmap2.put("pageno", 0);
        kmap2.put("pageSize", 4);

        List<Ftype> klist2 = ftypeService.getAll(kmap2);
       
        /*论坛*/
        map.put("list", list);

        /*新闻*/
        map.put("xlist", list2);
        /*寻亲登记*/
        map.put("glist", glist);
        /*指南*/
        map.put("splist", splist);
        /*用户*/
        map.put("ulist", ulist);
        


        map.put("klist2", klist2);
        typeListAll(map, "分类");
        typeListAll(map, "板块");
        return "index2";
    }

    // 分页
    @RequestMapping("newsListFore.do")
    public String newsListFore(@RequestParam(value = "page", required = false) String page, ModelMap map,
                               HttpSession session) {
        if (page == null || page.equals("")) {
            page = "1";
        }
        PageBean pageBean = new PageBean(Integer.parseInt(page), PageBean.PAGESIZE);
        Map<String, Object> pmap = new HashMap<String, Object>();
        pmap.put("pageno", pageBean.getStart());
        pmap.put("pageSize", pageBean.getPageSize());
        Map<String, Object> cmap = new HashMap<String, Object>();
        cmap.put("gtype", "新闻");
        pmap.put("gtype", "新闻");
        int total = newsService.getCount(cmap);
        System.out.println("total===" + total);
        pageBean.setTotal(total);
        List<News> list = newsService.getByPage(pmap);
        map.put("page", pageBean);
        map.put("list", list);
        session.setAttribute("p", 1);
        map.put("flist", ftypeService.getAll(null));
        typeListAll(map, "板块");
        typeListAll(map, "分类");
        return "newlist";
    }

    //推荐新闻
    @RequestMapping("newsListCommend.do")
    public String newsListCommend(ModelMap map, HttpSession session) {
        Map<String, Object> pmap = new HashMap<String, Object>();
        pmap.put("gtype", "新闻");
        pmap.put("status", "推荐");
        List<News> list = newsService.getAll(pmap);
        map.put("list", list);
        session.setAttribute("p", 1);
        map.put("flist", ftypeService.getAll(null));
        typeListAll(map, "板块");
        typeListAll(map, "分类");
        return "newlist";
    }

    // 搜索查询searchNews.do
    @RequestMapping("searchNews.do")
    public String searchNews(@RequestParam(value = "page", required = false) String page, ModelMap map,
                             HttpSession session, News news) {
        if (page == null || page.equals("")) {
            page = "1";
        }
        PageBean pageBean = new PageBean(Integer.parseInt(page), PageBean.PAGESIZE);
        Map<String, Object> pmap = new HashMap<String, Object>();
        pmap.put("pageno", pageBean.getStart());
        pmap.put("pageSize", pageBean.getPageSize());
        Map<String, Object> cmap = new HashMap<String, Object>();
        if (news.getName() != null && !news.getName().equals("")) {
            cmap.put("name", news.getName());
            pmap.put("name", news.getName());
        }
        if (news.getFid() != null && !news.getFid().equals("")) {
            cmap.put("fid", news.getFid());
            pmap.put("fid", news.getFid());
        }
        cmap.put("gtype", "新闻");
        pmap.put("gtype", "新闻");
        int total = newsService.getCount(cmap);
        System.out.println("total===" + total);
        pageBean.setTotal(total);
        List<News> list = newsService.getByPage(pmap);
        map.put("page", pageBean);
        map.put("list", list);
        session.setAttribute("p", 2);
        typeListAll(map, "板块");
        typeListAll(map, "分类");
        return "newlist";
    }

    // 新闻详情
    @RequestMapping("showNewx.do")
    public String showNews(ModelMap map, int id, HttpSession session) {
        System.out.println("id===" + id);
        News news = newsService.getById(id);
        //Ftype ftype = ftypeService.getById(Integer.parseInt(news.getGtype()));
        news.setHit(news.getHit() + 1);
        newsService.update(news);
        news = newsService.getById(id);
        /* 调用通用方法 */
        // recordAdd(session, id, ftype);
        map.put("news", news);
        Map<String, Object> bmap = new HashMap<>();
        bmap.put("gid", id);
        List<BbsWithBLOBs> blist = bbsService.getAll(bmap);
        List<Sysuser> ulist = userService.getAll(null);
        typeListAll(map, "板块");
        typeListAll(map, "分类");
        map.put("list", blist);
        map.put("ulist", ulist);
        return "newsx";
    }

   

    //指南

    @RequestMapping("newsListFore_zl.do")
    public String newsListFore_zl(@RequestParam(value = "page", required = false) String page, ModelMap map,
                                  HttpSession session) {
        if (page == null || page.equals("")) {
            page = "1";
        }
        PageBean pageBean = new PageBean(Integer.parseInt(page), PageBean.PAGESIZE);
        Map<String, Object> pmap = new HashMap<String, Object>();
        pmap.put("pageno", pageBean.getStart());
        pmap.put("pageSize", pageBean.getPageSize());
        Map<String, Object> cmap = new HashMap<String, Object>();
        cmap.put("gtype", "指南");
        pmap.put("gtype", "指南");
        int total = newsService.getCount(cmap);
        System.out.println("total===" + total);
        pageBean.setTotal(total);
        List<News> list = newsService.getByPage(pmap);
        map.put("page", pageBean);
        map.put("list", list);
        session.setAttribute("p", 1);
        map.put("flist", ftypeService.getAll(null));
        typeListAll(map, "板块");
        typeListAll(map, "分类");
        return "newlist_zl";
    }

    // 搜索查询searchNews.do
    @RequestMapping("searchNews_zl.do")
    public String searchNews_zl(@RequestParam(value = "page", required = false) String page, ModelMap map,
                                HttpSession session, News news) {
        if (page == null || page.equals("")) {
            page = "1";
        }
        PageBean pageBean = new PageBean(Integer.parseInt(page), PageBean.PAGESIZE);
        Map<String, Object> pmap = new HashMap<String, Object>();
        pmap.put("pageno", pageBean.getStart());
        pmap.put("pageSize", pageBean.getPageSize());
        Map<String, Object> cmap = new HashMap<String, Object>();
        if (news.getName() != null && !news.getName().equals("")) {
            cmap.put("name", news.getName());
            pmap.put("name", news.getName());
        }
        if (news.getGtype() != null && !news.getGtype().equals("")) {
            cmap.put("gtype", news.getGtype());
            pmap.put("gtype", news.getGtype());
        }
        if (news.getUid() != null && !news.getUid().equals("")) {
            cmap.put("uid", news.getUid());
            pmap.put("uid", news.getUid());
        }
        if (news.getFid() != null && !news.getFid().equals("")) {
            cmap.put("fid", news.getFid());
            pmap.put("fid", news.getFid());
        }
        cmap.put("gtype", "指南");
        pmap.put("gtype", "指南");
        int total = newsService.getCount(cmap);
        System.out.println("total===" + total);
        pageBean.setTotal(total);
        List<News> list = newsService.getByPage(pmap);
        map.put("page", pageBean);
        map.put("list", list);
        session.setAttribute("p", 2);
        typeListAll(map, "板块");
        typeListAll(map, "分类");
        return "newlist_zl";
    }

    // 指南详情
    @RequestMapping("showNewx_zl.do")
    public String showNews_zl(ModelMap map, int id, HttpSession session) {
        System.out.println("id===" + id);
        News news = newsService.getById(id);
        news.setHit(news.getHit() + 1);
        newsService.update(news);
        news = newsService.getById(id);
        map.put("news", news);
        Map<String, Object> bmap = new HashMap<>();
        bmap.put("gid", id);
        bmap.put("btype", "评论");
        List<BbsWithBLOBs> blist = bbsService.getAll(bmap);
        List<Sysuser> ulist = userService.getAll(null);
        typeListAll(map, "板块");
        typeListAll(map, "分类");
        map.put("list", blist);
        map.put("ulist", ulist);
        return "newsx_zl";
    }




    //论坛

    @RequestMapping("newsListFore_lt.do")
    public String newsListFore_lt(@RequestParam(value = "page", required = false) String page, ModelMap map,
                                  HttpSession session) {
        if (page == null || page.equals("")) {
            page = "1";
        }
        PageBean pageBean = new PageBean(Integer.parseInt(page), PageBean.PAGESIZE);
        Map<String, Object> pmap = new HashMap<String, Object>();
        pmap.put("pageno", pageBean.getStart());
        pmap.put("pageSize", pageBean.getPageSize());
        Map<String, Object> cmap = new HashMap<String, Object>();
        cmap.put("gtype", "论坛");
        pmap.put("gtype", "论坛");
        int total = newsService.getCount(cmap);
        System.out.println("total===" + total);
        pageBean.setTotal(total);
        List<News> list = newsService.getByPage(pmap);
        map.put("page", pageBean);
        map.put("list", list);
        session.setAttribute("p", 1);
        map.put("flist", ftypeService.getAll(null));
        typeListAll(map, "板块");
        typeListAll(map, "分类");
        return "newlist_lt";
    }

    // 搜索查询searchNews.do
    @RequestMapping("searchNews_lt.do")
    public String searchNews_lt(@RequestParam(value = "page", required = false) String page, ModelMap map,
                                HttpSession session, News news) {
        if (page == null || page.equals("")) {
            page = "1";
        }
        PageBean pageBean = new PageBean(Integer.parseInt(page), PageBean.PAGESIZE);
        Map<String, Object> pmap = new HashMap<String, Object>();
        pmap.put("pageno", pageBean.getStart());
        pmap.put("pageSize", pageBean.getPageSize());
        Map<String, Object> cmap = new HashMap<String, Object>();
        if (news.getName() != null && !news.getName().equals("")) {
            cmap.put("name", news.getName());
            pmap.put("name", news.getName());
        }
        if (news.getGtype() != null && !news.getGtype().equals("")) {
            cmap.put("gtype", news.getGtype());
            pmap.put("gtype", news.getGtype());
        }
        if (news.getUid() != null && !news.getUid().equals("")) {
            cmap.put("uid", news.getUid());
            pmap.put("uid", news.getUid());
        }
        if (news.getFid() != null && !news.getFid().equals("")) {
            cmap.put("fid", news.getFid());
            pmap.put("fid", news.getFid());
        }
        cmap.put("gtype", "论坛");
        pmap.put("gtype", "论坛");
        int total = newsService.getCount(cmap);
        System.out.println("total===" + total);
        pageBean.setTotal(total);
        List<News> list = newsService.getByPage(pmap);
        map.put("page", pageBean);
        map.put("list", list);
        session.setAttribute("p", 2);
        typeListAll(map, "板块");
        typeListAll(map, "分类");
        return "newlist_lt";
    }

    // 论坛详情
    @RequestMapping("showNewx_lt.do")
    public String showNews_lt(ModelMap map, int id, HttpSession session) {
        System.out.println("id===" + id);
        News news = newsService.getById(id);
        news.setHit(news.getHit() + 1);
        newsService.update(news);
        news = newsService.getById(id);
        map.put("news", news);
        Map<String, Object> bmap = new HashMap<>();
        bmap.put("gid", id);
        bmap.put("btype", "评论");
        List<BbsWithBLOBs> blist = bbsService.getAll(bmap);
        List<Sysuser> ulist = userService.getAll(null);
        typeListAll(map, "板块");
        typeListAll(map, "分类");
        map.put("list", blist);
        map.put("ulist", ulist);
        return "newsx_lt";
    }



    //前台发布论坛
    
    
    
    @RequestMapping("doAddNews_lt.do")
    public String doAddNews_lt(ModelMap map) {
        typeList(map);
        return "news_add_lt";
    }

    @RequestMapping("addNews_lt.do")
    public String addNews_lt(@RequestParam(value = "file", required = false) MultipartFile file,
                          HttpServletRequest request, News news, String img,HttpSession session) {
    	Sysuser user= (Sysuser) session.getAttribute("user");
    	if(user==null) {
    		return "login";
    	}else {
    		System.out.println("====+=====");
            img = fileUpload(file, request, img);
            news.setImg(img);
            Timestamp time = new Timestamp(System.currentTimeMillis());
            news.setPubtime(time.toString().substring(0, 19));
            news.setIsdel("1");
            news.setHit(0);
            news.setUid(user.getId());
            news.setGtype("论坛");
            news.setStatus("通过审核");
            newsService.add(news);
            return "redirect:newsList_lt.do";
    	}
        
    }

    @RequestMapping("doUpdateNews_lt.do")
    public String doUpdateNews_lt(ModelMap map, int id) {
        map.put("news", newsService.getById(id));
        typeList(map);
        return "news_update_lt";
    }
   

    
    @RequestMapping("updateNews_lt.do")
    public String updateNews_lt(@RequestParam(value = "file", required = false) MultipartFile file,
                             HttpServletRequest request, News news, String img) {
        img = fileUpload(file, request, img);
        if (img != "zanwu.jpg") {
            news.setImg(img);
        }
        newsService.update(news);
        return "redirect:newsList_lt.do";
    }

    // 分页查询
    @RequestMapping("newsList_lt.do")
    public String newsList_lt(@RequestParam(value = "page", required = false) String page, ModelMap map,
                           HttpSession session) {
    	Sysuser user= (Sysuser) session.getAttribute("user");
    	if(user==null) {
    		return "login";
    	}else {
        Map<String, Object> nMap = new HashMap<>();
        nMap.put("gtype", "论坛");
        nMap.put("uid", user.getId());
        List<News> list = newsService.getAll(nMap);
        map.put("list", list);
        typeList(map);
        return "news_list_lt";
    	}
    }

    // 分页模糊查询
    @RequestMapping("newsListSearch_lt.do")
    public String newsListSearch_lt(ModelMap map, News news,HttpSession session) {
    	Sysuser user= (Sysuser) session.getAttribute("user");
    	if(user==null) {
    		return "login";
    	}else {
        Map<String, Object> pmap = new HashMap<String, Object>();
        if (news.getName() != null && !news.getName().equals("")) {
            pmap.put("name", news.getName());
        }
        if (news.getGtype() != null && !news.getGtype().equals("")) {
            pmap.put("gtype", news.getGtype());
        }
        pmap.put("gtype", "论坛");
        List<News> list = newsService.getAll(pmap);
        typeList(map);
        map.put("list", list);
        return "news_list_lt";
    	}
    }

    @RequestMapping("deleteNews_lt.do")
    public String deleteNews_lt(int id) {
        newsService.delete(id);
        return "redirect:newsList_lt.do";
    }

  


    /**
     * ===新闻后台管理===
     *
     * @param map
     */

    /*审核*/
    @RequestMapping("admin/news_tongGuo.do")
    public String news_tongGuo(ModelMap map, int id) {
        News news = newsService.getById(id);
        news.setStatus("通过审核");
        newsService.update(news);
        return "admin/success";
    }

    /*审核*/
    @RequestMapping("admin/news_buTongGuo.do")
    public String news_buTongGuo(ModelMap map, int id) {
        News news = newsService.getById(id);
        news.setStatus("审核失败");
        newsService.update(news);
        return "admin/success";
    }
    
    
    public void typeList(ModelMap map) {
        Map<String, Object> tmap = new HashMap<>();
        tmap.put("btype", "板块");
        List<Ftype> tlist = ftypeService.getAll(tmap);
        map.put("tlist", tlist);
    }

    @RequestMapping("admin/doAddNews.do")
    public String doAddNews(ModelMap map) {
        typeList(map);
        return "admin/news_add";
    }

    @RequestMapping("admin/addNews.do")
    public String addNews(@RequestParam(value = "file", required = false) MultipartFile file,
                          HttpServletRequest request, News news, String img) {
        System.out.println("====+=====");
        img = fileUpload(file, request, img);
        news.setImg(img);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        news.setPubtime(time.toString().substring(0, 19));
        news.setIsdel("1");
        news.setHit(0);
        news.setGtype("新闻");
        newsService.add(news);
        return "admin/success";
    }

    @RequestMapping("admin/doUpdateNews.do")
    public String doUpdateNews(ModelMap map, int id) {
        map.put("news", newsService.getById(id));
        typeList(map);
        return "admin/news_update";
    }

    @RequestMapping("admin/updateNews.do")
    public String updateNews(@RequestParam(value = "file", required = false) MultipartFile file,
                             HttpServletRequest request, News news, String img) {
        img = fileUpload(file, request, img);
        if (img != "zanwu.jpg") {
            news.setImg(img);
        }
        newsService.update(news);
        return "redirect:newsList.do";
    }

    // 分页查询
    @RequestMapping("admin/newsList.do")
    public String goodList(@RequestParam(value = "page", required = false) String page, ModelMap map,
                           HttpSession session) {
        Map<String, Object> nMap = new HashMap<>();
        nMap.put("gtype", "新闻");
        List<News> list = newsService.getAll(nMap);
        map.put("list", list);
        typeList(map);
        return "admin/news_list";
    }

    // 分页模糊查询
    @RequestMapping("admin/newsListSearch.do")
    public String newsListSearch(ModelMap map, News news) {
        Map<String, Object> pmap = new HashMap<String, Object>();
        if (news.getName() != null && !news.getName().equals("")) {
            pmap.put("name", news.getName());
        }
        if (news.getGtype() != null && !news.getGtype().equals("")) {
            pmap.put("gtype", news.getGtype());
        }
        pmap.put("gtype", "新闻");
        List<News> list = newsService.getAll(pmap);
        typeList(map);
        map.put("list", list);
        return "admin/news_list";
    }

    @RequestMapping("admin/deleteNews.do")
    public String deleteNews(int id) {
        newsService.delete(id);
        return "redirect:newsList.do";
    }


   


    /**
     * ===指南后台管理===
     *
     * @param map
     */


    @RequestMapping("admin/doAddNews_zl.do")
    public String doAddNews_zl(ModelMap map) {
        /*通用方法*/
        typeListAll(map, "板块");
        typeListAll(map, "分类");
        return "admin/news_add_zl";
    }

    @RequestMapping("admin/addNews_zl.do")
    public String addNews_zl(@RequestParam(value = "file", required = false) MultipartFile file,
    		HttpServletRequest request, News news, String img, HttpSession session) {
        Sysuser sysuser = (Sysuser) session.getAttribute("auser");
        if (sysuser == null) {
            return "admin/login";
        } else {
            System.out.println("====+=====");
            img = fileUpload(file, request, img);
        
            news.setImg(img);
        
            Timestamp time = new Timestamp(System.currentTimeMillis());
            news.setPubtime(time.toString().substring(0, 19));
            news.setIsdel("1");
            news.setHit(0);
            news.setUid(sysuser.getId());
            news.setGtype("指南");
            if (sysuser.getUtype().equals("管理员")) {
                news.setStatus("通过审核");
            } else {
                news.setStatus("待审核");
            }
            newsService.add(news);
            return "admin/success";
        }
    }

    @RequestMapping("admin/doUpdateNews_zl.do")
    public String doUpdateNews_zl(ModelMap map, int id) {
    	/*通用方法*/
        typeListAll(map, "板块");
        typeListAll(map, "分类");
        map.put("news", newsService.getById(id));
        return "admin/news_update_zl";
    }

    @RequestMapping("admin/updateNews_zl.do")
    public String updateNews_zl(@RequestParam(value = "file", required = false) MultipartFile file,
                                HttpServletRequest request, News news, String img) {
        img = fileUpload(file, request, img);
        if (img != "zanwu.jpg") {
            news.setImg(img);
        }
        newsService.update(news);
        return "redirect:newsList_zl.do";
    }

    // 分页查询
    @RequestMapping("admin/newsList_zl.do")
    public String newsList_zl(@RequestParam(value = "page", required = false) String page, ModelMap map,
                              HttpSession session) {
        Sysuser sysuser = (Sysuser) session.getAttribute("auser");
        if (sysuser == null) {
            return "admin/login";
        } else {
            Map<String, Object> nMap = new HashMap<>();
            nMap.put("gtype", "指南");
            if (sysuser.getUtype().equals("用户")) {
                nMap.put("uid", sysuser.getId());
            }
            List<News> list = newsService.getAll(nMap);
            map.put("list", list);
            /*通用方法*/
            typeListAll(map, "板块");
            typeListAll(map, "分类");
            return "admin/news_list_zl";
        }
    }

    // 分页模糊查询
    @RequestMapping("admin/newsListSearch_zl.do")
    public String newsListSearch_zl(ModelMap map, News news, HttpSession session) {
        Sysuser sysuser = (Sysuser) session.getAttribute("auser");
        if (sysuser == null) {
            return "admin/login";
        } else {
            Map<String, Object> pmap = new HashMap<>();
            if (news.getName() != null && !news.getName().equals("")) {
                pmap.put("name", news.getName());
            }
            if (news.getGtype() != null && !news.getGtype().equals("")) {
                pmap.put("gtype", news.getGtype());
            }
            if (news.getFid() != null ) {
                pmap.put("fid", news.getFid());
            }
            if (sysuser.getUtype().equals("用户")) {
                pmap.put("uid", sysuser.getId());
            }
            pmap.put("gtype", "指南");
            List<News> list = newsService.getAll(pmap);
            typeList(map);
            map.put("list", list);
            /*通用方法*/
            typeListAll(map, "板块");
            typeListAll(map, "分类");
            return "admin/news_list_zl";
        }
    }

    @RequestMapping("admin/deleteNews_zl.do")
    public String deleteNews_zl(int id) {
        newsService.delete(id);
        return "redirect:newsList_zl.do";
    }
    
    
    
    

    /**
     * ===论坛后台管理===
     *
     * @param map
     */


   
    // 分页查询
    @RequestMapping("admin/newsList_lt.do")
    public String newsList_ltA(@RequestParam(value = "page", required = false) String page, ModelMap map,
                              HttpSession session) {
        Sysuser sysuser = (Sysuser) session.getAttribute("auser");
        if (sysuser == null) {
            return "admin/login";
        } else {
            Map<String, Object> nMap = new HashMap<>();
            nMap.put("gtype", "论坛");
            if (sysuser.getUtype().equals("用户")) {
                nMap.put("uid", sysuser.getId());
            }
            List<News> list = newsService.getAll(nMap);
            map.put("list", list);
            /*通用方法*/
            typeListAll(map, "板块");
            typeListAll(map, "分类");
            return "admin/news_list_lt";
        }
    }

    // 分页模糊查询
    @RequestMapping("admin/newsListSearch_lt.do")
    public String newsListSearch_ltA(ModelMap map, News news, HttpSession session) {
        Sysuser sysuser = (Sysuser) session.getAttribute("auser");
        if (sysuser == null) {
            return "admin/login";
        } else {
            Map<String, Object> pmap = new HashMap<>();
            if (news.getName() != null && !news.getName().equals("")) {
                pmap.put("name", news.getName());
            }
            if (news.getGtype() != null && !news.getGtype().equals("")) {
                pmap.put("gtype", news.getGtype());
            }
            if (news.getFid() != null ) {
                pmap.put("fid", news.getFid());
            }
            if (sysuser.getUtype().equals("用户")) {
                pmap.put("uid", sysuser.getId());
            }
            pmap.put("gtype", "论坛");
            List<News> list = newsService.getAll(pmap);
            typeList(map);
            map.put("list", list);
            /*通用方法*/
            typeListAll(map, "板块");
            typeListAll(map, "分类");
            return "admin/news_list_lt";
        }
    }

    @RequestMapping("admin/deleteNews_lt.do")
    public String deleteNews_ltA(int id) {
        newsService.delete(id);
        return "redirect:newsList_lt.do";
    }


    @RequestMapping("admin/doUpdateNews_look.do")
    public String doUpdateNews_look(ModelMap map, int id) {
        map.put("news", newsService.getById(id));
        typeList(map);
        return "admin/newsx";
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
