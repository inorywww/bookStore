let vm = new Vue({
    el: "#app",
    data: {
        searchContent: "",
        dataShow: "",
        totalNum: 0,
    },
    mounted: function () {
        this.searchBook();
    },
    methods: {
        searchBook: function () {
            // 解码为中文 获取搜索内容
            this.searchContent = decodeURI(document.URL.split("?")[1].split("=")[1]);
            // let s = this.searchContent
            // console.log(this.searchContent);
            // // console.log($("#searchID"));
            // $("#header").load("header.html",function () {
            //     $("#searchID").val(s);
            // })

            let data = new URLSearchParams();
            data.append('searchContent', this.searchContent)
            //axios
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/SearchBook";
            axios.post(url, data).then((res) => {
                console.log(res);
                this.dataShow = res.data;
                this.totalNum = this.dataShow.length;
            }).catch((err) => {
                console.log("失败！");
            });
        },
        addToCart: function (index) {
            let account = getCok("account");
            if (account !== "") {
                let id = this.dataShow[index].id;
                addCart(id);
                $(".control-label").text('加入购物车成功！');
                let options = {
                    "backdrop": "true"
                };
                $('#myModal').modal(options);
            } else {
                $(".control-label").text('未登录，请登录后重试！');
                let options = {
                    "backdrop": "true"
                };
                $('#myModal').modal(options);
            }
        },
        moreInfo: function (index) {
            let id = this.dataShow[index].id;
            console.log("id:" + id)
            window.location.href = "bookDetail.html?id=" + id;
        },
    }
})