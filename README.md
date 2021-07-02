# 网上书店+书店管理系统

## 前言：

该项目为学校综合课程设计，因为时间太紧，一共两周时间，需要实现用户前台+管理系统+后端以及小程序端（小程序会放在另一个仓库），其中中途还有两门考试，真是** ╥﹏╥。。

所以前端就是拿了以前刚学vue时候练手做的小demo改了一下，直接使用了原生Vue，没有进行重构。而后端则是拿了本学期刚学的servlet，用起来也没有那么不方便，毕竟前后端也是完全分离的 。

虽然项目不大，还有很多瑕疵，但是一人完成全栈开发也还是学到了很多东西。

## 功能概述：

本系统用户分为顾客和管理员。

### 顾客

- 顾客进入网上书店的主页，可以浏览书店里所有的书籍，如果想购买必须先在该网站注册。注册完成后，登录即可在网站购买书籍。
- 注册登录完成后，可以直接在主页进行图书挑选并购买，也可以进入图书查询界面通过图书类别、图书名称、作者名字三种方式查询图书，查到满意的图书后可点击购买。
- 顾客也具有管理个人信息功能，比如管理购物车、管理订单、信息修改等。

### 管理员

- 管理者可以通过具有管理权限的账户登录后台管理界面，实现对图书、订单、用户信息的查看以及增删改查。

## 技术实现

- 前端：原生Vue+bootstrap
- 后端：java servlet+jdbc连接数据库
- 数据库：sql server

## 小程序端
请点击https://github.com/inorywww/WeChat-bookSotre

## 部分截图
因为页面太多，这里只放部分界面图。

![image](https://github.com/inorywww/bookStore/blob/master/images/%E5%89%8D%E5%8F%B0%E4%B8%BB%E9%A1%B5.png)

![image](https://github.com/inorywww/bookStore/blob/master/images/%E8%B4%AD%E7%89%A9%E8%BD%A6.png)

![image](https://github.com/inorywww/bookStore/blob/master/images/%E4%B8%AA%E4%BA%BA%E4%B8%AD%E5%BF%83.png)

![image](https://github.com/inorywww/bookStore/blob/master/images/%E5%90%8E%E5%8F%B0%E7%AE%A1%E7%90%86%E4%B8%BB%E9%A1%B5.png)

![image](https://github.com/inorywww/bookStore/blob/master/images/%E5%89%8D%E5%8F%B0%E4%B8%BB%E9%A1%B5.png)
