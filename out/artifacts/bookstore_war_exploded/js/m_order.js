let vm = new Vue({
    el: "#app",
    data: {
        allOrders: {},
        showData: {},
    },
    mounted: function () {
        this.getBookInfo();
    },
    methods: {
        getBookInfo: function () {
            //axios
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/getAllOrder";
            axios.post(url).then((res) => {
                console.log("成功！");
                console.log(res);
                this.allOrders = res.data;
                this.showData = JSON.parse(JSON.stringify(this.allOrders));
            }).catch((err) => {
                console.log("失败！");
            });
        },
        moreInfo:function (index) {
            let orderID = this.showData[index].orderID;
            window.location.href = "orderDetail.html?orderID=" + orderID;
        }
    }
})