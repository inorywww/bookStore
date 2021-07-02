let vm = new Vue({
    el: "#app",
    data: {
        account: "",
        password: "",
    },
    mounted:function(){
        let account = getCok("account");
        // 如果已经登录，则直接跳转到个人中心
        if (account !== "") {
            window.location.href = "center.html"
        }
    },
    methods: {
        login: function () {
            let data = new URLSearchParams();
            data.append('account', this.account);
            data.append('password', this.password);

            //vue-resource
            // const res = await this.$http.post('http://localhost:8080/bookstore_war_exploded/servlets/LoginAuthentication', data);
            // console.log(res)

            //axios
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/login";
            axios.post(url, data).then((res) => {
                console.log("成功！");
                console.log(res);
                if (res.data === "success") {
                    if (this.account === "root"){
                        window.location.href="m_index.html";
                    }
                    else {
                        window.location.href="index.html";
                    }
                    // 设置登录cookie
                    document.cookie="account="+this.account;
                }
                else {
                    let options = {
                        "backdrop": "true"
                    };
                    $('#myModal').modal(options);
                }
            }).catch((err) => {
                console.log("失败！");
            });
        },
    }
})