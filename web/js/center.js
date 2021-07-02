let vm = new Vue({
    el: "#app",
    data: {
        userInfo: {},
        accountInfo: {
            oldPassword: "",
            newPassword: "",
            newPasswordRe: "",
        },
        orderInfo: {},
        upInfo: {
            accName: "",
            tel: "",
            address: "",
        }
    },
    mounted: function () {
        this.getUserInfo();
        this.getOrderInfo();
    },
    methods: {
        getUserInfo: function () {
            //axios
            let data = new URLSearchParams();
            data.append('account',getCok('account'))
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/getUserInfo";
            axios.post(url,data).then((res) => {
                console.log("成功！");
                console.log(res);
                this.userInfo = res.data;
            }).catch((err) => {
                console.log("失败！");
            });
        },
        hideInput: function (str) {
            let input = '#' + str + "Input";
            let show = '#' + str + 'Show';
            if ($(input).is(":visible")) {
                $(input).hide();
                $(show).show();
                if($(input).val()!==""){
                    $(input).val("")
                }
            }
        },
        updateInfo: function (str) {
            let input = '#' + str + "Input";
            let show = '#' + str + 'Show';
            console.log(str);
            if ($(show).is(":visible")) {
                $(show).hide();
                $(input).show();
            } else {
                let data = new URLSearchParams();
                data.append('account',this.userInfo.account)
                data.append('accName',this.upInfo.accName)
                data.append('tel',this.upInfo.tel)
                data.append('address',this.upInfo.address)
                console.log(data);
                let url = "http://localhost:8080/bookstore_war_exploded/servlets/updateInfo";
                axios.post(url, data).then((res) => {
                    console.log("成功！");
                    console.log(res);
                    this.userInfo = res.data;
                    $(input).hide();
                    $(show).show();
                    if($(input).val()!==""){
                        $(input).val("")
                    }
                }).catch((err) => {
                    console.log("失败！");
                });

            }

        },
        updatePwd: function () {
            let data = new URLSearchParams();
            data.append('oldPassword', this.accountInfo.oldPassword);
            data.append("newPassword", this.accountInfo.newPassword);
            data.append("newPasswordRe", this.accountInfo.newPasswordRe);
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/updatePwd";
            axios.post(url, data).then((res) => {
                console.log("成功！");
                console.log(res);
                if (res.data === "OldPasswordError") {
                    console.log("OldPasswordError")
                } else if (res.data === "newPasswordError") {
                    console.log("newPasswordError")
                } else {
                    console.log("success");
                    this.accountInfo = {};
                }
            }).catch((err) => {
                console.log("失败！");
            });
        },
        getOrderInfo: function () {
            let data = new URLSearchParams();
            data.append('account',getCok('account'))
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/getOrderInfo";
            axios.post(url,data).then((res) => {
                console.log("成功！");
                console.log(res);
                this.orderInfo = res.data;
            }).catch((err) => {
                console.log("失败！");
            });
        },
        submitOrder: function (index) {
            let orderID = this.orderInfo[index].orderID;
            let data = new URLSearchParams();
            data.append('orderID', orderID);
            data.append('account', this.userInfo.account);
            data.append('way', 'submit');
            //axios
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/orderAction";
            axios.post(url, data).then((res) => {
                console.log("成功！");
                console.log(res);
                this.orderInfo = res.data;
            }).catch((err) => {
                console.log("失败！");
            });
        },
        viewOrder: function (index) {
            let orderID = this.orderInfo[index].orderID;
            window.location.href = "orderDetail.html?orderID=" + orderID;
        },
        delOrder: function (index) {
            let orderID = this.orderInfo[index].orderID;
            console.log(orderID);
            let data = new URLSearchParams();
            data.append('orderID', orderID);
            data.append('way', 'del');
            //axios
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/orderAction";
            axios.post(url, data).then((res) => {
                console.log("成功！");
                console.log(res);
                this.orderInfo = res.data;
            }).catch((err) => {
                console.log("失败！");
            });
        },

    }
})