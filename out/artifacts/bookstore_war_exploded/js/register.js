let vm = new Vue({
    el: "#app",
    data: {
        account:"",
        password:"",
        passwordRe:"",
    },
    methods: {
        registerAccount:function(){
            let data = new URLSearchParams();
            data.append('account', this.account);
            data.append("password",this.password);
            data.append("passwordRe",this.passwordRe);
            //axios
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/register";
            axios.post(url, data).then((res) => {
                console.log("成功！");
                console.log(res);
            }).catch((err) => {
                console.log("失败！");
            });
        },
    }
})