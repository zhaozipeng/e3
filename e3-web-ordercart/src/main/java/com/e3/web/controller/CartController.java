package com.e3.web.controller;

import com.e3.service.goods.pojo.TbItem;
import com.e3.service.goods.service.ItemService;
import com.e3.service.ordercart.service.CartService;
import com.e3.service.user.pojo.TbUser;
import com.e3.utils.E3Result;
import com.e3.utils.JedisUtils;
import com.e3.utils.JsonUtils;
import com.e3.utils.TextUtils;
import com.e3.web.utils.CookieUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/4.
 */
@Controller
public class CartController {
    private static final String E3_CART = "E3_CART";
    @Resource
    CartService cartService;
    @Resource
    ItemService itemService;

    //添加购物车
    @RequestMapping("/cart/add/{itemId}")
    public String cartAdd(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request) {
        //用户中登录的情况下  存入redis中
            //先判断是否登录
        TbUser user = (TbUser) request.getAttribute("user");
            if(user!=null){
                //将商品信息添加到redis中 （除了cookie都写中service）
                cartService.addRedisCart(itemId,num,user.getId());

                return "redirect:/cart/cartSuccess";

            }
        //1-1用户没有登录的情况下  信息是添加中cookie中

        //1-2先获取cookie中的所有商品的信息(凡是cookie的操作都在controller中)
        List<TbItem> itemList = getCookieItemList(request);

        //1-3判断cookie中是否有这个商品
        //便利集合
        //设置个布尔类型来判断
        boolean flag = false;

        for (TbItem s : itemList
                ) {
            if (s.getId() == itemId.longValue()) {//上面是long类型 是比较不出来的 所以要longCalues
                //如果有修改数量\
                //1-4如果有就只修改数量
                s.setNum(s.getNum() + num);
                flag = true;

                break;
            }
        }
        //1-5将信息添加进cookie中
        if (flag = false) {
            TbItem tbItem = itemService.selectItemById(itemId);
            tbItem.setNum(num);
            itemList.add(tbItem);
        }


        //1-6然后将cookie信息添加回浏览器

        return "redirect:/cart/cartSuccess";
    }

    //解决重复添加商品
    @RequestMapping("/cart/cartSuccess")
    public String cartSuccess() {
        return "cartSuccess";
    }

    public List<TbItem> getCookieItemList(HttpServletRequest request) {

        /*
        *
        *
        *
        * */
        String cookieValue = CookieUtils.getCookieValue(request, E3_CART, true);
        if (!TextUtils.isEmpty(cookieValue)) {
            return JsonUtils.jsonToList(cookieValue, TbItem.class);


        }

        //如果购物车中没商品
        return new ArrayList<TbItem>();
    }

    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public E3Result updateNum(HttpServletRequest request, HttpServletResponse response, @PathVariable Long itemId, @PathVariable Integer num) {
        List<TbItem> itemList = getCookieItemList(request);

        for (TbItem s : itemList
                ) {
            if (s.getId() == itemId.longValue()) {//上面是long类型 是比较不出来的 所以要longCalues
                //如果有修改数量\
                //1-4如果有就只修改数量
                s.setNum(s.getNum() + num);

                break;
            }}
            //修改完再重新写cookie
        CookieUtils.setCookie(request, response, E3_CART, JsonUtils.objectToJson(itemList), 24 * 60 * 60, true);

        return E3Result.ok(null);

    }

    //删除
    @RequestMapping("/cart/delete/{itemId}")
    public String deleteItem(HttpServletRequest request, HttpServletResponse response,@PathVariable Long itemId){
        List<TbItem> itemList = getCookieItemList(request);

        for (TbItem s : itemList
                ) {
            if (s.getId() == itemId.longValue()) {//上面是long类型 是比较不出来的 所以要longCalues
                //如果有修改数量\
                //1-4如果有就只修改数量
                 itemList.remove(s);
                break;
            }}
        CookieUtils.setCookie(request, response, E3_CART, JsonUtils.objectToJson(itemList), 24 * 60 * 60, true);

        return "redirect:/cart/cart";
    }

    @RequestMapping("/cart/cart")
    public String showCart(HttpServletRequest request, Model model) {
        List<TbItem> cartList=null;
        //当没有登录的情况下查看购物车
        TbUser user = (TbUser) request.getAttribute("user");
        if(user!=null){
            //从redis取

            cartList = cartService.getRedisCart(user.getId());

        }else {
            //从cookie取
            cartList = getCookieItemList(request);
        }
        model.addAttribute("cartList", cartList);
        return "cart";
    }
}