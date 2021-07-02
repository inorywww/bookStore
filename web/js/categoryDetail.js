let vm = new Vue({
    el:"#app",
    data:{
        allBooks: {},
        showData: {},

    },
    mounted: function () {
        this.getBookInfo();
    },
    methods:{
        getBookInfo:function () {
            let index = decodeURI(document.URL.split("?")[1].split("=")[1]);
            //axios
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/getBookInfo";
            axios.post(url).then((res) => {
                console.log("成功！");
                console.log(res);
                this.allBooks = res.data;
                this.showData = JSON.parse(JSON.stringify(this.allBooks))[index];
                console.log(this.showData)
            }).catch((err) => {
                console.log("失败！");
            });
        },
        moreInfo:function (index) {
            let id = this.showData.contents[index].id;
            console.log("id:"+id)
            window.location.href="bookDetail.html?id="+id;
        },
        addToCart:function (index) {
            let account = getCok("account");
            if (account !== "") {
                let id = this.showData.contents[index].id;
                let data = new URLSearchParams();
                data.append('id', id)
                //axios
                let url = "http://localhost:8080/bookstore_war_exploded/servlets/addToCart";
                axios.post(url, data).then((res) => {
                    console.log("成功！");
                    console.log(res);
                    // 弹出提示框
                    $(".control-label").text('加入购物车成功');
                    let options = {
                        "backdrop" : "true"
                    };
                    $('#myModal').modal(options);
                }).catch((err) => {
                    console.log("失败！");
                });
            }
            else {
                $(".control-label").text('未登录，请登录后重试');
                let options = {
                    "backdrop" : "true"
                };
                $('#myModal').modal(options);
            }
        },

    }
})