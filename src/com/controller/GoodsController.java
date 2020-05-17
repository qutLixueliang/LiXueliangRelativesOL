package com.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
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

import com.entity.Ftype;
import com.entity.Goods;
import com.entity.Sysuser;
import com.server.BbsServer;
import com.server.FtypeServer;
import com.server.GoodsServer;
import com.server.SysuserServer;
import com.util.PageBean;

@Controller
public class GoodsController {
    @Resource
    private GoodsServer goodsService;
    @Resource
    private SysuserServer userService;
    @Resource
    private FtypeServer ftypeService;
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
        umap.put("utype","志愿者");
        umap.put("vtype","空闲中");
        map.put("ulist",userService.getAll(umap));
        map.put("ualist",userService.getAll(null));
    }




    @RequestMapping("doGoodsAdd.do")
    public String doGoodsAdd(ModelMap map) {
        typeListAll(map, "分类");
        return "goods_add";
    }

    @RequestMapping("goodsAdd.do")
    public String goodsAdd(@RequestParam(value = "file", required = false) MultipartFile file,
                           @RequestParam(value = "file2", required = false) MultipartFile file2,
                           HttpServletRequest request, Goods goods, HttpSession session, String img, String img2) {
        Sysuser user = (Sysuser)session.getAttribute("user");
        if (user==null){
            return "login";
        }else {
            img = fileUpload(file, request, img);
            goods.setImg(img);
            img2 = fileUpload(file2,request,img2);
            goods.setUpload(img2);
            Timestamp time = new Timestamp(System.currentTimeMillis());
            goods.setPubtime(time.toString().substring(0, 19));
            goods.setHit(0);
            goods.setUid(user.getId());
            Sysuser sysuser = userService.getById(goods.getZyid());
            sysuser.setVtype("服务中");
            userService.update(sysuser);
            goods.setStime(time.toString().substring(0, 19));
            goods.setStatus("待处理");
            goodsService.add(goods);
            return "redirect:myGoodsList.do";
        }

    }



    @RequestMapping("doGoodsUpdate.do")
    public String doGoodsUpdate(ModelMap map, int id) {
        map.put("goods", goodsService.getById(id));
        typeListAll(map, "分类");
        return "goods_update";
    }

    /*完成服务*/
    @RequestMapping("doGoodsUpdate_wanCheng.do")
    public String doGoodsUpdate_wanCheng(ModelMap map, int id) {
        Goods goods = goodsService.getById(id);
        goods.setStatus("已完成");
        Sysuser user = userService.getById(goods.getZyid());
        user.setVtype("空闲中");
        userService.update(user);
        goodsService.update(goods);
        return "redirect:myGoodsList_zy.do";
    }

    @RequestMapping("goodsUpdate.do")
    public String goodsUpdate(@RequestParam(value = "file", required = false) MultipartFile file,
                              @RequestParam(value = "file2", required = false) MultipartFile file2,
                              HttpServletRequest request, Goods goods, HttpSession session, String img, String img2) {
        img = fileUpload(file, request, img);
        if (img != null) {
            goods.setImg(img);
        }
        img2 = fileUpload(file2,request,img2);
        if (img2!=null){
            goods.setUpload(img2);
        }
        Goods goods1 = goodsService.getById(goods.getId());
        Sysuser sysuser = userService.getById(goods1.getZyid());
        sysuser.setVtype("空闲中");
        userService.update(sysuser);
        Sysuser sysuser1 = userService.getById(goods.getZyid());
        sysuser1.setVtype("服务中");
        userService.update(sysuser1);
        goodsService.update(goods);
        return "redirect:myGoodsList.do";
    }

    @RequestMapping("deleteGoodsFore.do")
    public String deleteGoodsFore(int id) {
        Goods goods = goodsService.getById(id);
        Sysuser sysuser = userService.getById(goods.getZyid());
        sysuser.setVtype("空闲中");
        userService.update(sysuser);
        goodsService.delete(id);
        return "redirect:myGoodsList.do";
    }




    @RequestMapping("myGoodsList.do")
    public String myGoodsList(@RequestParam(value = "page", required = false) String page, ModelMap map,
                              HttpSession session) {
        Sysuser uu = (Sysuser) session.getAttribute("user");
        if (uu == null || uu.equals("")) {
            return "login";
        } else {
            countDays();
            if (page == null || page.equals("")) {
                page = "1";
            }
            PageBean pageBean = new PageBean(Integer.parseInt(page), PageBean.PAGESIZE);
            Map<String, Object> pmap = new HashMap<String, Object>();
            pmap.put("pageno", pageBean.getStart());
            pmap.put("pageSize", pageBean.getPageSize());
            Map<String, Object> cmap = new HashMap<String, Object>();
            cmap.put("uid", uu.getId());
            pmap.put("uid", uu.getId());
            int total = goodsService.getCount(cmap);
            pageBean.setTotal(total);
            List<Goods> list = goodsService.getAll(pmap);
            map.put("page", pageBean);
            map.put("list", list);
            session.setAttribute("p", 1);
            map.put("ulist", userService.getAll(null));
            map.put("glist", goodsService.getAll(null));
            typeListAll(map, "类型");
            typeListAll(map, "分类");
            return "myGoodsList";
        }
    }

    /*志愿*/
    @RequestMapping("myGoodsList_zy.do")
    public String myGoodsList_zy(@RequestParam(value = "page", required = false) String page, ModelMap map,
                              HttpSession session) {
        Sysuser uu = (Sysuser) session.getAttribute("user");
        if (uu == null || uu.equals("")) {
            return "login";
        } else {
            countDays();
            if (page == null || page.equals("")) {
                page = "1";
            }
            PageBean pageBean = new PageBean(Integer.parseInt(page), PageBean.PAGESIZE);
            Map<String, Object> pmap = new HashMap<String, Object>();
            pmap.put("pageno", pageBean.getStart());
            pmap.put("pageSize", pageBean.getPageSize());
            Map<String, Object> cmap = new HashMap<String, Object>();
            cmap.put("zyid", uu.getId());
            pmap.put("zyid", uu.getId());
            int total = goodsService.getCount(cmap);
            pageBean.setTotal(total);
            List<Goods> list = goodsService.getAll(pmap);
            map.put("page", pageBean);
            map.put("list", list);
            session.setAttribute("p", 1);
            map.put("ulist", userService.getAll(null));
            map.put("glist", goodsService.getAll(null));
            typeListAll(map, "类型");
            typeListAll(map, "分类");
            return "myGoodsList_zy";
        }
    }







    @RequestMapping("showGoodsx.do")
    public String showGoodsDetail(HttpServletRequest request, int id, ModelMap map, HttpSession session) {
        Goods goods = goodsService.getById(id);
        goods.setHit(goods.getHit() + 1);
        goodsService.update(goods);
        goods = goodsService.getById(id);
        map.put("goods", goods);
        typeListAll(map, "板块");
        typeListAll(map, "分类");
        return "goodsx";
    }

    // 分页
    @RequestMapping("goodsListFore.do")
    public String goodsListFore(@RequestParam(value = "page", required = false) String page, ModelMap map,
                                HttpSession session) {
        countDays();
        if (page == null || page.equals("")) {
            page = "1";
        }
        PageBean pageBean = new PageBean(Integer.parseInt(page), PageBean.PAGESIZE);
        Map<String, Object> pmap = new HashMap<String, Object>();
        pmap.put("pageno", pageBean.getStart());
        pmap.put("pageSize", pageBean.getPageSize());
        Map<String, Object> cmap = new HashMap<String, Object>();
        cmap.put("gtype", null);
        pmap.put("gtype", null);
      /*  pmap.put("status", "通过审核");
        cmap.put("status", "通过审核");*/
        int total = goodsService.getCount(cmap);
        System.out.println("total===" + total);
        pageBean.setTotal(total);
        List<Goods> list = goodsService.getByPage(pmap);
        map.put("page", pageBean);
        map.put("list", list);
        session.setAttribute("p", 1);
        map.put("flist", ftypeService.getAll(null));
        typeListAll(map, "板块");
        typeListAll(map, "分类");
        return "goodslist";
    }

    @RequestMapping("goodsListCommend.do")
    public String goodsListCommend(ModelMap map,HttpSession session) {
        countDays();
        Map<String, Object> pmap = new HashMap<>();
        pmap.put("status", "通过审核");
        pmap.put("mark", "推荐");
        List<Goods> list = goodsService.getAll(pmap);
        map.put("list", list);
        map.put("flist", ftypeService.getAll(null));
        typeListAll(map, "板块");
        typeListAll(map, "分类");
        return "goodslist";
    }

    @RequestMapping("searchGoods.do")
    public String searchGoods(@RequestParam(value = "page", required = false) String page, ModelMap map,
                              HttpSession session, Goods goods) {
        countDays();
        if (page == null || page.equals("")) {
            page = "1";
        }
        PageBean pageBean = new PageBean(Integer.parseInt(page), PageBean.PAGESIZE);
        Map<String, Object> pmap = new HashMap<String, Object>();
        pmap.put("pageno", pageBean.getStart());
        pmap.put("pageSize", pageBean.getPageSize());
        Map<String, Object> cmap = new HashMap<String, Object>();
        if (goods.getName() != null && !goods.getName().equals("")) {
            cmap.put("name", goods.getName());
            pmap.put("name", goods.getName());
        }
        if (goods.getFid() != null && !goods.getFid().equals("")) {
            cmap.put("fid", goods.getFid());
            pmap.put("fid", goods.getFid());
        }
        if (goods.getUid() != null && !goods.getUid().equals("")) {
            cmap.put("uid", goods.getUid());
            pmap.put("uid", goods.getUid());
        }
        /*pmap.put("status", "通过审核");
        cmap.put("status", "通过审核");*/
        int total = goodsService.getCount(cmap);
        System.out.println("total===" + total);
        pageBean.setTotal(total);
        List<Goods> list = goodsService.getByPage(pmap);
        map.put("page", pageBean);
        map.put("list", list);
        session.setAttribute("p", 2);
        typeListAll(map, "板块");
        typeListAll(map, "分类");
        return "goodslist";
    }

    @RequestMapping("deleteGoods.do")
    public String deleteGoods2(int id) {
        goodsService.delete(id);
        return "redirect:showAllGoods.do";
    }

    /**
     * ===后台管理===
     *
     * @return
     */

    public void typeList(ModelMap map) {
        Map<String, Object> tmap = new HashMap<>();
        tmap.put("btype", "分类");
        List<Ftype> tlist = ftypeService.getAll(tmap);
        map.put("tlist", tlist);
    }

    //通过审核
    @RequestMapping("admin/goods_tongGuo.do")
    public String goods_tongGuo(ModelMap map, int id) {
        Goods goods = goodsService.getById(id);
        goods.setStatus("通过审核");
        goodsService.update(goods);
        return "redirect:goodsList.do";
    }

    //不通过审核
    @RequestMapping("admin/goods_buTongGuo.do")
    public String goods_buTongGuo(ModelMap map, int id) {
        Goods goods = goodsService.getById(id);
        goods.setStatus("审核失败");
        goodsService.update(goods);
        return "redirect:goodsList.do";
    }


    @RequestMapping("admin/doAddGoods.do")
    public String doAddGoods(ModelMap map) {
        /*通用方法*/
        typeListAll(map, "板块");
        typeListAll(map, "分类");
        return "admin/goods_add";
    }

    @RequestMapping("admin/addGoods.do")
    public String addGoods(@RequestParam(value = "file", required = false) MultipartFile file,
    		@RequestParam(value = "file2", required = false) MultipartFile file2,
                           HttpServletRequest request, Goods goods, HttpSession session, String img, String img2) {
        Sysuser sysuser = (Sysuser)session.getAttribute("auser");
        if (sysuser==null){
            return "admin/login";
        }else {
            img = fileUpload(file, request, img);
            goods.setImg(img);
            img2=fileUpload(file2, request, img2);
            goods.setUpload(img2);
            goods.setUid(sysuser.getId());
            Timestamp time = new Timestamp(System.currentTimeMillis());
            goods.setPubtime(time.toString().substring(0, 19));
            goods.setHit(0);
            if (sysuser.getUtype().equals("管理员")){
                goods.setStatus("通过审核");
            }else {
                goods.setStatus("待审核");
            }
            goodsService.add(goods);
            return "admin/success";
        }
    }

    @RequestMapping("admin/doUpdateGoods.do")
    public String doUpdateGoods(ModelMap map, int id) {
        map.put("goods", goodsService.getById(id));
        typeList(map);
        return "admin/goods_update";
    }

    @RequestMapping("admin/updateGoods.do")
    public String updateGoods(@RequestParam(value = "file", required = false) MultipartFile file,
    		@RequestParam(value = "file2", required = false) MultipartFile file2,
                              HttpServletRequest request, Goods goods, HttpSession session, String img, String img2) {
        img = fileUpload(file, request, img);
        if (img != null) {
            goods.setImg(img);
        }
        img2= fileUpload(file2, request, img2);
        if(img2!=null) {
        	goods.setUpload(img2);
        }
        goodsService.update(goods);
        return "redirect:goodsList.do";
    }

    // 分页查询---分类
    @RequestMapping("admin/goodsList.do")
    public String fgoodsList(ModelMap map, HttpSession session, Goods goods) {
        countDays();
        Map<String, Object> pmap = new HashMap<String, Object>();
        Sysuser sysuser = (Sysuser)session.getAttribute("auser");
        if (sysuser==null){
            return "admin/login";
        }else {
            if (sysuser.getUtype().equals("用户")){
                pmap.put("uid",sysuser.getId());
            }
            List<Goods> list = goodsService.getAll(pmap);
            map.put("list", list);
            /*通用方法*/
            typeListAll(map, "板块");
            typeListAll(map, "分类");
            return "admin/goods_list";
        }
    }

    @RequestMapping("admin/goodsListSearch.do")
    public String goodsListSearch(ModelMap map, HttpSession session, Goods goods) {
        Sysuser sysuser = (Sysuser)session.getAttribute("auser");
        if (sysuser==null){
            return "admin/login";
        }else {
            countDays();
            Map<String, Object> pmap = new HashMap<String, Object>();
            if (goods.getName() != null && !goods.getName().equals("")) {
                pmap.put("name", goods.getName());
            }
            if (goods.getFid() != null && !goods.getFid().equals("")) {
                pmap.put("fid", goods.getFid());
            }
            if (sysuser.getUtype().equals("用户")){
                pmap.put("uid",sysuser.getId());
            }
            List<Goods> list = goodsService.getAll(pmap);
            map.put("list", list);
            /*通用方法*/
            typeListAll(map, "板块");
            typeListAll(map, "分类");
            return "admin/goods_list";
        }
    }

    @RequestMapping("admin/deleteGoods.do")
    public String deleteGoods(int id) {
        goodsService.delete(id);
        return "redirect:goodsList.do";
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
            img = null;
        }

        return img;

    }


    /* 浏览记录通用方法 */
    public void recordAdd(HttpSession session, int id, Ftype ftype) {
        /*Sysuser user = (Sysuser) session.getAttribute("user");
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
                    record.setGtype("通过审核");
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

    public static int getBetweenDayNumber(String dateA, String dateB) {
        long dayNumber = 0;
        //1小时=60分钟=3600秒=3600000
        //long mins = 60L * 1000L;
        //long hour =60 * 60L * 1000L;
        long day= 24L * 60L * 60L * 1000L;//计算天数之差
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            java.util.Date d1 = df.parse(dateA);
            java.util.Date d2 = df.parse(dateB);
            dayNumber = (d2.getTime() - d1.getTime()) / day;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (int) dayNumber;
    }

    /*通用方法计算天数*/
    public  void countDays(){
        /*Timestamp time = new Timestamp(System.currentTimeMillis());
        List<Goods> glist = goodsService.getAll(null);
        int  day = 0;
        for (Goods g : glist) {
            day = getBetweenDayNumber(g.getEtime(), time.toString().substring(0,19));
            if (day <= 0) {
                g.setSday(-day);
                //g.setStatus("还有"+-day+"天过期");
            } else if (day>0) {
                g.setSday(0);
                if(g.getPrice()>g.getYprice()){
                    g.setStatus("通过审核失败");
                }else{
                    g.setStatus("通过审核完成");
                }
            }
            goodsService.update(g);
        }*/

    }


    /**
     * 得到多少天之后之前的日期
     *
     * @param  date
     * @param   day
     * @return
     */
    public static String getDay(String date, int day) {
        String b = date.substring(0, 10);
        String c = b.substring(0, 4);
        String d = b.substring(5, 7);
        String f = b.substring(8, 10);
        String aa = c + "/" + d + "/" + f;
        String a = "";
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        GregorianCalendar grc = new GregorianCalendar();
        grc.setTime(new Date(aa));
        grc.add(GregorianCalendar.DAY_OF_MONTH, day);
        String resu = dateFormat.format(grc.getTime());
        String t[] = resu.split("-");
        String sesuu = "";
        for (int i = 0; i < t.length; i++) {
            if (t[i].length() == 1) {
                t[i] = "0" + t[i];
            }
            sesuu += t[i] + "-";
        }

        return sesuu.substring(0, 10);
    }


    /**
     * 计算两个时期之间的天数
     */
    public static int dayToday(String DATE1, String DATE2) {
        int i = 0;
        if (DATE1.indexOf(" ") > -1)
            DATE1 = DATE1.substring(0, DATE1.indexOf(" "));
        if (DATE2.indexOf(" ") > -1)
            DATE2 = DATE2.substring(0, DATE2.indexOf(" "));

        String[] d1 = DATE1.split("-");
        if (d1[1].length() == 1) {
            DATE1 = d1[0] + "-0" + d1[1];
        } else {
            DATE1 = d1[0] + "-" + d1[1];
        }

        if (d1[2].length() == 1) {
            DATE1 = DATE1 + "-0" + d1[2];
        } else {
            DATE1 = DATE1 + "-" + d1[2];
        }

        String[] d2 = DATE2.split("-");
        if (d2[1].length() == 1) {
            DATE2 = d2[0] + "-0" + d2[1];
        } else {
            DATE2 = d2[0] + "-" + d2[1];
        }

        if (d2[2].length() == 1) {
            DATE2 = DATE2 + "-0" + d2[2];
        } else {
            DATE2 = DATE2 + "-" + d2[2];
        }


        for (int j = 0; j < 10000; j++) {
            i = j;
            String gday = getDay(DATE1, j);
            if (gday.equals(DATE2)) {
                break;
            }
        }
        return i;
    }
}
