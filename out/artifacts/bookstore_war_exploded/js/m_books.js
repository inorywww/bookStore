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
        showBooks: {},
        // 所有页面的数据
        totalPage: [],
        //全部数据
        allBooks: {},
        editBookInfo: {
            id: "",
            name: "",
            author: "",
            amount: "",
            price: "",
            category: "",
            pDate: "",
            press: "",
            pages: "",
            ISBN: "",
            introduction: ""
        },
        addBookInfo: {
            name: "",
            author: "",
            amount: "",
            price: "",
            category: "",
            pDate: "",
            press: "",
            pages: "",
            ISBN: "",
            introduction: ""
        },
        delIndex: "",
        categoryBook: {},
    },
    mounted: function () {
        this.getBookInfo();
        this.getCategoryBook();
        let delIndex = this.delIndex;
        $('#myModalDel').on('hidden.bs.modal', function (e) {
            console.log("关闭模态框");
            console.log(delIndex);
            delIndex = "";
            console.log("-------")
            console.log(delIndex);
        });
        this.delIndex = delIndex;
    },
    methods: {
        getBookInfo: function () {
            //axios
            console.log("getBookInfo");
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/getAllBooks";
            axios.post(url).then((res) => {
                console.log("成功！");
                console.log(res);
                this.allBooks = res.data;
                this.showBooks = JSON.parse(JSON.stringify(this.allBooks));
                this.pagination();
            }).catch((err) => {
                console.log("失败！");
            });
        },
        moreInfo: function (index) {
            let id = this.showData[index].id;
            window.location.href = "m_bookDetail.html?id=" + id;
        },
        isEdit: function (index) {
            for (let k in this.editBookInfo) {
                this.editBookInfo[k] = this.showData[index][k];
            }
        },
        editInfo: function () {
            let data = new URLSearchParams();
            for (let k in this.editBookInfo) {
                data.append(k, this.editBookInfo[k]);
            }
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/editBookInfo";
            axios.post(url, data).then((res) => {
                console.log("成功！");
                console.log(res);
                this.allBooks = res.data;
                this.showBooks = JSON.parse(JSON.stringify(this.allBooks));
                this.pagination();
                this.getCategoryBook();
                for (let i in this.editBookInfo) {
                    this.editBookInfo[i] = "";
                }
            }).catch((err) => {
                console.log("失败！");
            });
        },
        addBook: function () {
            let data = new URLSearchParams();
            for (let k in this.addBookInfo) {
                console.log(this.addBookInfo[k]);
                data.append(k, this.addBookInfo[k]);
            }
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/addBook";
            axios.post(url, data).then((res) => {
                console.log("成功！");
                console.log(res);
                this.allBooks = res.data;
                this.showBooks = JSON.parse(JSON.stringify(this.allBooks));
                this.pagination();
                for (let i in this.addBookInfo) {
                    this.addBookInfo[i] = "";
                }
                this.getCategoryBook();

            }).catch((err) => {
                console.log("失败！");
            });
        },
        isDel: function (index) {
            console.log("isDel");
            console.log(index);
            this.delIndex = index;
            console.log(this.delIndex);
        },
        delBook: function () {
            let id = this.showData[this.delIndex].id;
            //axios
            let data = new URLSearchParams();
            data.append("id", id)
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/delBook";
            axios.post(url, data).then((res) => {
                console.log("成功！");
                console.log(res);
                this.allBooks = res.data;
                this.showBooks = JSON.parse(JSON.stringify(this.allBooks));
                this.pagination();
                this.getCategoryBook();
            }).catch((err) => {
                console.log("失败！");
            });
        },
        //获取分类后的书
        getCategoryBook: function () {
            //axios
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/getBookInfo";
            axios.post(url).then((res) => {
                console.log("成功！");
                console.log(res);
                this.categoryBook = res.data;
            }).catch((err) => {
                console.log("失败！");
            });
        },
        //分类显示
        showCategoryBook: function (index) {
            if (index === "all") {
                this.showBooks = JSON.parse(JSON.stringify(this.allBooks));
            } else {
                this.showBooks = JSON.parse(JSON.stringify(this.categoryBook[index].contents))
            }
            this.pagination();
        },
        searchBook: function () {
            if (this.searchContent !== "") {
                let data = new URLSearchParams();
                data.append("searchContent", this.searchContent)
                let url = "http://localhost:8080/bookstore_war_exploded/servlets/SearchBook";
                axios.post(url, data).then((res) => {
                    console.log("成功！");
                    console.log(res);
                    this.showBooks = res.data;
                }).catch((err) => {
                    console.log("失败！");
                });
            } else {
                this.showBooks = JSON.parse(JSON.stringify(this.allBooks));
            }
            this.pagination();
        },
        //分页
        pagination: function () {
            // 根据后台数据的条数和每页显示数量算出一共几页,得0时设为1
            this.pageNum = Math.ceil(this.showBooks.length / this.pageSize) || 1;
            for (let i = 0; i < this.pageNum; i++) {
                // 每一页都是一个数组 形如 [['第一页的数据'],['第二页的数据'],['第三页数据']]
                // 根据每页显示数量 将后台的数据分割到 每一页,假设pageSize为5， 则第一页是1-5条，即slice(0,5)，第二页是6-10条，即slice(5,10)...
                this.totalPage[i] = this.showBooks.slice(this.pageSize * i, this.pageSize * (i + 1))
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