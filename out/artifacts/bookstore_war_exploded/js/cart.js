let vm = new Vue({
    el: "#app",
    data: {
        allBooks: {},
        showData: {},
        totalNum: 0,
        totalPrice: 0,
    },
    mounted: function () {
        this.getCartInfo();
    },
    methods: {
        getCartInfo: function () {
            //axios
            console.log("getCartInfo");
            let account = getCok("account");
            if (account !== "") {
                let url = "http://localhost:8080/bookstore_war_exploded/servlets/getCartInfo";
                axios.post(url).then((res) => {
                    console.log("成功！");
                    console.log(res);
                    this.allBooks = res.data;
                    this.showData = JSON.parse(JSON.stringify(this.allBooks))
                    this.changeTotal()
                }).catch((err) => {
                    console.log("失败！");
                });
            }

        },
        //增加减少书的数目
        changeNum: function (index, way) {
            let id = this.showData[index].id;
            let data = new URLSearchParams();
            data.append("id", id)
            data.append("way", way)
            if (way === "-") {
                this.showData[index].buy_num -= 1;
            } else {
                this.showData[index].buy_num += 1;
            }
            this.changeTotal();
            //axios
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/changeCartNum";
            axios.post(url, data).then((res) => {
                console.log("成功！");
                console.log(res);
            }).catch((err) => {
                console.log("失败！");
            });
        },
        delBook: function (index) {
            let id = this.showData[index].id;
            //axios
            let data = new URLSearchParams();
            data.append("id", id)
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/delCartBook";
            axios.post(url, data).then((res) => {
                console.log("成功！");
                console.log(res);
                this.allBooks = res.data;
                this.showData = JSON.parse(JSON.stringify(this.allBooks))
                this.changeTotal()
            }).catch((err) => {
                console.log("失败！");
            });
        },
        changeTotal: function () {
            this.totalNum = 0;
            this.totalPrice = 0;
            for (let i = 0; i < this.showData.length; i++) {
                this.totalNum += this.showData[i].buy_num;
                this.totalPrice += (this.showData[i].buy_num * this.showData[i].price);
            }
        },
        submitOrder: function () {
            let account = getCok("account");
            let data = new URLSearchParams();
            data.append('account', account);
            data.append('way', 'submitOrder');
            //axios
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/submitOrder";
            axios.post(url,data).then((res) => {
                console.log("成功！");
                console.log(res);
                this.allBooks = res.data;
                this.showData = JSON.parse(JSON.stringify(this.allBooks))
                this.changeTotal()
                let options = {
                    "backdrop": "true"
                };
                $('#myModal').modal(options);
            }).catch((err) => {
                console.log("失败！");
            });
        },
        toOrder:function () {
            window.location.href = "center.html#orderManager"
        }
    }
})