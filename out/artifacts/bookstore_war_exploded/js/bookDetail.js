let vm = new Vue({
    el: "#app",
    data: {
        showData: {},
    },
    mounted: function () {
        this.getBookDetail();
    },
    methods: {
        getBookDetail: function () {
            // 书的id
            let id = document.URL.split("?")[1].split("=")[1];
            let data = new URLSearchParams();
            data.append('id', id)
            //axios
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/getBookDetail";
            axios.post(url, data).then((res) => {
                console.log("成功！");
                console.log(res);
                this.showData = res.data;
            }).catch((err) => {
                console.log("失败！");
            });
        },
        addToCart:function () {
            let account = getCok("account");
            if (account !== "") {
                let id = this.showData.id;
                addCart(id);
                $(".control-label").text('加入购物车成功！');
                let options = {
                    "backdrop" : "true"
                };
                $('#myModal').modal(options);
            }
            else {
                $(".control-label").text('未登录，请登录后重试！');
                let options = {
                    "backdrop" : "true"
                };
                $('#myModal').modal(options);
            }
        }
    }
})