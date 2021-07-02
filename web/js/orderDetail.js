let vm = new Vue({
    el: "#app",
    data: {
        orderInfo: {},
        userInfo:{},
        bookInfo:{},
    },
    mounted: function () {
        this.getOrderDetail()
    },
    methods: {
        getOrderDetail: function () {
            let orderID = decodeURI(document.URL.split("?")[1].split("=")[1]);
            let data = new URLSearchParams();
            data.append('orderID', orderID);
            //axios
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/getOrderDetail";
            axios.post(url,data).then((res) => {
                console.log("成功！");
                console.log(res);
                // this.orderInfo = res.data;
                this.bookInfo = res.data.books;
                this.orderInfo = res.data.order;
                this.userInfo = res.data.user;
            }).catch((err) => {
                console.log("失败！");
            });
        },
        delOrder:function () {
            let orderID = decodeURI(document.URL.split("?")[1].split("=")[1]);
            let data = new URLSearchParams();
            data.append('orderID', orderID);
            data.append('way', 'del');
            //axios
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/orderAction";
            axios.post(url, data).then((res) => {
                console.log("成功！");
                console.log(res);
                window.location.href = "center.html#orderManager"
            }).catch((err) => {
                console.log("失败！");
            });
        }
    }
})