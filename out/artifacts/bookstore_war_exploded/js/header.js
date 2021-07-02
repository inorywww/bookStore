let vm = new Vue({
    el: "#app1",
    data() {
        return {
            searchContent: "",
            //0 ：未登录
            //1 已登录
            loginStatus: 0,
        }
    },
    mounted: function () {
        let account = getCok("account");
        if (account !== "") {
            this.loginStatus = 1;
        }
    },
    methods: {
        SetSearchContent: function () {
            if (this.searchContent !== "") {
                // 是search页面 不跳转新页面 刷新页面
                $("#searchID").val(this.searchContent);
                if (document.URL.search("search") !== -1) {
                    window.location.href = "search.html?searchContent=" + this.searchContent
                } else { // 不是search页面 跳转新页面
                    window.open("search.html?searchContent=" + this.searchContent);
                }
            }
        },
        toCenter: function () {
            if (this.loginStatus !== 0) {
                window.location.href = "center.html";
            } else {
                window.location.href = "login.html";
            }
        },
        loginOut: function () {
            let account = getCok("account");
            if (account !== "") {
                this.loginStatus = 0;
                let d = new Date();
                d.setTime(d.getTime() + (-1 * 24 * 60 * 60 * 1000));
                let expires = "expires=" + d.toUTCString();
                document.cookie = 'account' + "=" + "" + "; " + expires;
                window.location.href = "index.html"
            }
        },

        loginCheck: function () {
            let account = getCok("account");
            //已登录
            if (account !== "") {
                $(".control-label2").text('已登录，请勿重复登录！');

                let options = {
                    "backdrop": "true"
                };
                $('#myModal2').modal(options);

            } else {
                window.location.href = "login.html"
            }
        },
        cartCheck: function () {
            let account = getCok("account");
            //已登录
            if (account !== "") {
                window.location.href = "cart.html"
            } else {
                $(".control-label2").text('未登录，请登录后重试！');
                let options = {
                    "backdrop": "true"
                };
                $('#myModal2').modal(options);
            }
        }
    }
})