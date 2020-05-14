package com.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.entity.Goods;
import com.server.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.BbsWithBLOBs;
import com.entity.Ftype;
import com.entity.Sysuser;

@Controller
public class BbsController {
    @Resource
    private BbsServer bbsService;
    @Resource
    private SysuserServer userService;
    @Resource
    private NewsServer newsService;
    @Resource
    private FtypeServer ftypeService;
    @Resource
    private GoodsServer goodsService;

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




    // 点赞信息




    // 用户点赞
    

    @RequestMapping("addKeep.do")
    public String addKeep( ModelMap map,HttpSession session,int gid) {
        Sysuser u = (Sysuser) session.getAttribute("user");
        if (u == null || u.equals("")) {
            return "login";
        } else {
            Map<String, Object> pmap = new HashMap<String, Object>();
            pmap.put("uid", u.getId());
            pmap.put("gid", gid);
            pmap.put("gtype", "寻亲");
            pmap.put("btype", "点赞");
            int count = bbsService.getCount(pmap);
            if (count>0){
                return "redirect:myKeep.do";
            }else {
                Timestamp time = new Timestamp(System.currentTimeMillis());
                BbsWithBLOBs bbs = new BbsWithBLOBs();
                bbs.setUid(u.getId());
                bbs.setGid(gid);
                Goods goods = goodsService.getById(gid);
                if (goods.getZnum()!=null){
                    goods.setZnum(goods.getZnum()+1);
                }else {
                    goods.setZnum(1);
                }
                bbs.setGtype("寻亲");
                bbs.setBtype("点赞");
                bbs.setPubtime(time.toString().substring(0,19));
                goodsService.update(goods);
                bbsService.add(bbs);
                typeListAll(map, "分类");
                typeListAll(map, "板块");
                return "redirect:myKeep.do";
            }


        }
    }

   

    @RequestMapping("myKeep.do")
    public String myKeep(@RequestParam(value = "page", required = false) String page, ModelMap map,
                          HttpSession session) {
        Sysuser u = (Sysuser) session.getAttribute("user");
        if (u == null || u.equals("")) {
            return "login";
        } else {
            Map<String, Object> pmap = new HashMap<String, Object>();
            pmap.put("uid", u.getId());
            pmap.put("btype", "点赞");
            List<BbsWithBLOBs> list = bbsService.getAll(pmap);
            map.put("list", list);
            map.put("nlist", goodsService.getAll(null));
            typeListAll(map, "分类");
            typeListAll(map, "板块");
            return "myKeepList";
        }
    }


    @RequestMapping("deleteKeep.do")
    public String deleteKeep(int id) {
        bbsService.delete(id);
        return "redirect:myKeep.do";
    }

//我的好友


    @RequestMapping("addHaoYou.do")
    public String addHaoYou( ModelMap map,HttpSession session,int hid) {
        Sysuser u = (Sysuser) session.getAttribute("user");
        if (u == null || u.equals("")) {
            return "login";
        } else {
            Map<String, Object> pmap = new HashMap<String, Object>();
            pmap.put("uid", u.getId());
            pmap.put("id", hid);
            pmap.put("gtype", "好友");
            pmap.put("btype", "好友");
            int count = bbsService.getCount(pmap);
            if (count>0){
                return "redirect:myHaoYouList.do";
            }else {
                Timestamp time = new Timestamp(System.currentTimeMillis());
                BbsWithBLOBs bbs = new BbsWithBLOBs();
                bbs.setUid(u.getId());
                bbs.setHid(hid);
                bbs.setGtype("好友");
                bbs.setBtype("好友");
                bbs.setPubtime(time.toString().substring(0,19));
                bbsService.add(bbs);
                typeListAll(map, "分类");
                typeListAll(map, "板块");
                return "redirect:myHaoYouList.do";
            }


        }
    }



    @RequestMapping("myHaoYouList.do")
    public String myHaoYouList(@RequestParam(value = "page", required = false) String page, ModelMap map,
                         HttpSession session) {
        Sysuser u = (Sysuser) session.getAttribute("user");
        if (u == null || u.equals("")) {
            return "login";
        } else {
            Map<String, Object> pmap = new HashMap<String, Object>();
            pmap.put("uid", u.getId());
            pmap.put("btype", "好友");
            List<BbsWithBLOBs> list = bbsService.getAll(pmap);
            map.put("list", list);
            typeListAll(map, "分类");
            typeListAll(map, "板块");
            return "myHaoYouList";
        }
    }


    @RequestMapping("deleteHaoYou.do")
    public String deleteHaoYou(int id) {
        bbsService.delete(id);
        return "redirect:myHaoYouList.do";
    }


    //留言



    // 分页查询个人留言信息
    @RequestMapping("myBbsList.do")
    public String myBbsList(@RequestParam(value = "page", required = false) String page, ModelMap map,
                          HttpSession session) {
        Sysuser u = (Sysuser) session.getAttribute("user");
        if (u == null || u.equals("")) {
            return "login";
        } else {
            Map<String, Object> pmap = new HashMap<String, Object>();
            pmap.put("uid", u.getId());
            pmap.put("btype", "留言");
            List<BbsWithBLOBs> list = bbsService.getAll(pmap);
            map.put("list", list);
            map.put("nlist", newsService.getAll(null));
            typeListAll(map, "分类");
            typeListAll(map, "板块");
            return "myBbsList";
        }
    }

    @RequestMapping("deleteBbs.do")
    public String deleteBbs(int id) {
        bbsService.delete(id);
        return "redirect:myBbsList.do";
    }

    // 用户留言
    @RequestMapping("addBbs.do")
    public String addBbs(HttpServletRequest request, BbsWithBLOBs bbs, HttpSession session) {
        Sysuser u = (Sysuser) session.getAttribute("user");
        Timestamp time = new Timestamp(System.currentTimeMillis());
        if (u == null) {
            return "login";
        } else {
            bbs.setUid(u.getId());
            bbs.setSta("待回复");
            bbs.setBtype("留言");
            bbs.setPubtime(time.toString().substring(0, 19));
            bbsService.add(bbs);
            return "redirect:myBbsList.do";
        }

    }

    
    /*评论*/
    @RequestMapping("addBbs_pl.do")
    public String addBbs_pl(HttpServletRequest request, BbsWithBLOBs bbs, HttpSession session) {
        Sysuser u = (Sysuser) session.getAttribute("user");
        Timestamp time = new Timestamp(System.currentTimeMillis());
        if (u == null) {
            return "login";
        } else {
            bbs.setUid(u.getId());
            bbs.setSta("已评论");
            bbs.setPubtime(time.toString().substring(0, 19));
            bbsService.add(bbs);
            return "redirect:showNewx_lt.do?id="+bbs.getGid();
        }

    }
 // 分页查询个人留言信息
    @RequestMapping("myBbsList_pl.do")
    public String myBbsList_pl(@RequestParam(value = "page", required = false) String page, ModelMap map,
                          HttpSession session) {
        Sysuser u = (Sysuser) session.getAttribute("user");
        if (u == null || u.equals("")) {
            return "login";
        } else {
            Map<String, Object> pmap = new HashMap<String, Object>();
            pmap.put("uid", u.getId());
            pmap.put("btype", "评论");
            List<BbsWithBLOBs> list = bbsService.getAll(pmap);
            map.put("list", list);
            map.put("nlist", newsService.getAll(null));
            typeListAll(map, "分类");
            typeListAll(map, "板块");
            return "myBbsList_pl";
        }
    }

    @RequestMapping("deleteBbs_pl.do")
    public String deleteBbs_pl(int id) {
        bbsService.delete(id);
        return "redirect:myBbsList_pl.do";
    }
    
    
    
    // 回复建议反馈
    @RequestMapping("admin/addBbs.do")
    public String addOrder(HttpServletRequest request, BbsWithBLOBs bbs, HttpSession session) {
        Sysuser u = (Sysuser) session.getAttribute("auser");
        if (u == null) {
            return "admin/login";
        } else {
            bbs.setHid(u.getId());
            bbs.setSta("已回复");
            bbsService.update(bbs);
            return "admin/success";
        }
    }




    @RequestMapping("admin/doUpdateBbs.do")
    public String doUpdateBbs(HttpServletRequest request, int id, ModelMap map) {
        map.put("bbs", bbsService.getById(id));
        return "admin/bbs_update";
    }

    @RequestMapping("admin/updateBbs.do")
    public String updateBbs(BbsWithBLOBs bbs) {
        bbs.setSta("已回复");
        bbsService.update(bbs);
        return "redirect:bbsList.do";
    }

    // 分页查询 留言信息的列表
    @RequestMapping("admin/bbsList.do")
    public String bbsList(@RequestParam(value = "page", required = false) String page, ModelMap map,
                           HttpSession session) {
        Sysuser sysuser = (Sysuser)session.getAttribute("auser");
        if (sysuser==null){
            return "admin/login";
        }else {
            Map<String, Object> bmap = new HashMap<>();
            bmap.put("btype", "留言");
            if (sysuser.getUtype().equals("老师")){
                bmap.put("hid", sysuser.getId());
            }
            List<BbsWithBLOBs> list = bbsService.getByPage(bmap);
            map.put("list", list);
            typeListAll(map, "分类");
            typeListAll(map, "板块");
            return "admin/bbs_list";
        }
    }



    @RequestMapping("admin/deleteBbs.do")
    public String deleteBbs2(int id) {
        bbsService.delete(id);
        return "admin/success";
    }
}
