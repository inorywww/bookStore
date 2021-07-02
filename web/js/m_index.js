let vm = new Vue({
    el: "#app",
    data: {
        countInfo: {
            userCount: "",
            bookCount: "",
            orderCount: ""
        },
        newBooks: {},
        newOrder: {},
    },
    mounted: function () {
        this.getCount();
        this.getNewBook();
        this.getNewOrder();
    },
    methods: {
        getCount: function () {
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/getCount";
            axios.post(url).then((res) => {
                console.log("成功！");
                console.log(res);
                this.countInfo = res.data;
            }).catch((err) => {
                console.log("失败！");
            });
        },
        getNewBook: function () {
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/getNewBook";
            axios.post(url).then((res) => {
                console.log("成功！");
                console.log(res);
                this.newBooks = res.data;
            }).catch((err) => {
                console.log("失败！");
            });
        },
        getNewOrder: function () {
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/getNewOrder";
            axios.post(url).then((res) => {
                console.log("成功！");
                console.log(res);
                this.newOrder = res.data;
            }).catch((err) => {
                console.log("失败！");
            });
        },
        moreBookInfo: function (index) {
            let id = this.newBooks[index].id;
            window.location.href = "m_bookDetail.html?id=" + id;
        },
        moreOrderInfo: function (index) {
            let orderID = this.newOrder[index].orderID;
            window.location.href = "m_oderDetail.html?orderID=" + orderID;
        }
    }
})