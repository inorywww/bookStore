let vm = new Vue({
    el: "#app",
    data: {
        searchContent: "",
        // 每页显示数量
        pageSize: 5,
        // 共几页
        pageNum: 1,
        // 默认当前显示第一页
        currentPage: 0,
        //当前显示的数据
        showData: {},
        // 所有可供显示的数据
        showOrders: {},
        // 所有页面的数据
        totalPage: [],
        //全部数据
        allOrders: {},
        editOrderInfo: {
            orderID: "",
            account: "",
            status: "",
            buy_num:"",
            totalPrice:"",
            booksID: "",
            booksNum: "",
        },
        addOrderInfo: {
            account: "",
            status: "",
            booksID: "",
            booksNum: "",
        },
        delIndex: "",
        categoryOrder: {},
    },
    mounted: function () {
        this.getOrderInfo();
        this.getCategoryOrder();
        let delIndex = this.delIndex;
        $('#myModalDel').on('hidden.bs.modal', function (e) {
            delIndex = "";
        });
        this.delIndex = delIndex;
    },
    methods: {
        getOrderInfo: function () {
            //axios
            console.log("getBookInfo");
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/getAllOrder";
            axios.post(url).then((res) => {
                console.log("成功！");
                console.log(res);
                this.allOrders = res.data;
                this.showOrders = JSON.parse(JSON.stringify(this.allOrders));
                this.pagination();
            }).catch((err) => {
                console.log("失败！");
            });
        },
        moreInfo: function (index) {
            let orderID = this.showData[index].orderID;
            window.location.href = "m_oderDetail.html?orderID=" + orderID;
        },
        isEdit: function (index) {
            for (let k in this.editOrderInfo) {
                this.editOrderInfo[k] = this.showData[index][k];
            }
        },
        editInfo: function () {
            let data = new URLSearchParams();
            for (let k in this.editOrderInfo) {
                data.append(k, this.editOrderInfo[k]);
            }
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/editOrderInfo";
            axios.post(url, data).then((res) => {
                console.log("成功！");
                console.log(res);
                this.allOrders = res.data;
                this.showOrders = JSON.parse(JSON.stringify(this.allOrders));
                this.pagination();
                this.getCategoryOrder();
                for (let i in this.editOrderInfo) {
                    this.editOrderInfo[i] = "";
                }
            }).catch((err) => {
                console.log("失败！");
            });
        },
        addOrder: function () {
            let data = new URLSearchParams();
            for (let k in this.addOrderInfo) {
                console.log(this.addOrderInfo[k]);
                data.append(k, this.addOrderInfo[k]);
            }
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/addOrder";
            axios.post(url, data).then((res) => {
                console.log("成功！");
                console.log(res);
                this.allOrders = res.data;
                this.showOrders = JSON.parse(JSON.stringify(this.allOrders));
                this.pagination();
                for (let i in this.addOrderInfo) {
                    this.addOrderInfo[i] = "";
                }
                this.getCategoryOrder();
            }).catch((err) => {
                console.log("失败！");
            });
        },
        isDel: function (index) {
            this.delIndex = index;
        },
        delOrder: function () {
            let orderID = this.showData[this.delIndex].orderID;
            //axios
            let data = new URLSearchParams();
            data.append("orderID", orderID)
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/delOrder";
            axios.post(url, data).then((res) => {
                console.log("成功！");
                console.log(res);
                this.allOrders = res.data;
                this.showOrders = JSON.parse(JSON.stringify(this.allOrders));
                this.pagination();
                this.getCategoryOrder();
            }).catch((err) => {
                console.log("失败！");
            });
        },
        //获取分类后的书
        getCategoryOrder: function () {
            //axios
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/getOrderCategory";
            axios.post(url).then((res) => {
                console.log("成功！");
                console.log(res);
                this.categoryOrder = res.data;
            }).catch((err) => {
                console.log("失败！");
            });
        },
        //分类显示
        showCategoryOrder: function (index) {
            if (index === "all") {
                this.showOrders = JSON.parse(JSON.stringify(this.allOrders));
            } else {
                this.showOrders = JSON.parse(JSON.stringify(this.categoryOrder[index].contents))
            }
            this.pagination();
        },
        searchOrder: function () {
            if (this.searchContent !== "") {
                let data = new URLSearchParams();
                data.append("searchContent", this.searchContent)
                let url = "http://localhost:8080/bookstore_war_exploded/servlets/SearchBook";
                axios.post(url, data).then((res) => {
                    console.log("成功！");
                    console.log(res);
                    this.showOrders = res.data;
                }).catch((err) => {
                    console.log("失败！");
                });
            } else {
                this.showOrders = JSON.parse(JSON.stringify(this.allOrders));
            }
            this.pagination();
        },
        //分页
        pagination: function () {
            // 根据后台数据的条数和每页显示数量算出一共几页,得0时设为1
            this.pageNum = Math.ceil(this.showOrders.length / this.pageSize) || 1;
            for (let i = 0; i < this.pageNum; i++) {
                // 每一页都是一个数组 形如 [['第一页的数据'],['第二页的数据'],['第三页数据']]
                // 根据每页显示数量 将后台的数据分割到 每一页,假设pageSize为5， 则第一页是1-5条，即slice(0,5)，第二页是6-10条，即slice(5,10)...
                this.totalPage[i] = this.showOrders.slice(this.pageSize * i, this.pageSize * (i + 1))
            }
            // 获取到数据后显示第一页内容
            this.showData = this.totalPage[this.currentPage];
            $(".page").eq(0).addClass("active");
            $(".page").eq(0).siblings().removeClass("active");
        },
        // 上一页
        prePage: function () {
            if (this.currentPage === 0)
                return;
            this.showData = this.totalPage[--this.currentPage];
            let ps = $(".page")
            for (let i = 0; i < ps.length; i++) {
                if (ps.eq(i).is(".active")) {
                    ps.eq(i - 1).addClass("active");
                    ps.eq(i - 1).siblings().removeClass("active");
                    break;
                }
            }
        },
        // 下一页
        nextPage: function () {
            if (this.currentPage === this.pageNum - 1)
                return;
            this.showData = this.totalPage[++this.currentPage];
            let ps = $(".page")
            for (let i = 0; i < ps.length; i++) {
                if (ps.eq(i).is(".active")) {
                    ps.eq(i + 1).addClass("active");
                    ps.eq(i + 1).siblings().removeClass("active");
                    break;
                }
            }
        },
        jumpPage: function (index) {
            this.showData = this.totalPage[index];
            let ps = $(".page");
            ps.eq(index).addClass("active");
            ps.eq(index).siblings().removeClass("active");
        },
    },

})