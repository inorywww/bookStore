let vm = new Vue({
    el:"#app",
    data:{
        searchContent: "",
        // 每页显示数量
        pageSize: 5,
        // 共几页
        pageNum: 1,
        // 默认当前显示第一页
        currentPage: 0,
        //当前显示的数据
        showData: {},
        // 所有可供显示的数据
        showUsers: {},
        // 所有页面的数据
        totalPage: [],
        //全部数据
        allUsers: {},
        delIndex :0,
        addInfo:{
            account:"",
            password:"",
            accName:"",
            tel:"",
            address:"",
        },
        editUserInfo:{
            account:"",
            password:"",
            accName:"",
            tel:"",
            address:"",
        }
    },
    mounted:function(){
        this.getUserInfo();
    },
    methods:{
        getUserInfo: function () {
            //axios
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/getAllUser";
            axios.post(url).then((res) => {
                console.log("成功！");
                console.log(res);
                this.allUsers = res.data;
                this.showUsers = JSON.parse(JSON.stringify(this.allUsers));
                this.pagination();
            }).catch((err) => {
                console.log("失败！");
            });
        },
        addUser:function(){
            let data = new URLSearchParams();
            for (let k in this.addInfo) {
                console.log(this.addInfo[k]);
                data.append(k, this.addInfo[k]);
            }
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/addUser";
            axios.post(url, data).then((res) => {
                console.log("成功！");
                console.log(res);
                if (res.data!=="账户已存在"){
                    this.allUsers = res.data;
                    this.showUsers = JSON.parse(JSON.stringify(this.allUsers));
                    this.pagination();
                    for (let i in this.addInfo) {
                        this.addInfo[i] = "";
                    }
                    this.getCategoryBook();
                }
                else {
                    alert("账户已存在!");
                }

            }).catch((err) => {
                console.log("失败！");
            });
        },
        isEdit: function (index) {
            console.log("idEdit")
            for (let k in this.editUserInfo) {
                this.editUserInfo[k] = this.showData[index][k];
            }
        },
        editInfo: function () {
            let data = new URLSearchParams();
            for (let k in this.editUserInfo) {
                data.append(k, this.editUserInfo[k]);
            }
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/editUserInfo";
            axios.post(url, data).then((res) => {
                console.log("成功！");
                console.log(res);
                this.allUsers = res.data;
                this.showUsers = JSON.parse(JSON.stringify(this.allUsers));
                this.pagination();
                for (let i in this.editUserInfo) {
                    this.editUserInfo[i] = "";
                }
            }).catch((err) => {
                console.log("失败！");
            });
        },
        isDel: function (index) {
            console.log("isDel");
            console.log(index);
            this.delIndex = index;
            console.log(this.delIndex);
        },
        delUser: function () {
            let account = this.showData[this.delIndex].account;
            //axios
            let data = new URLSearchParams();
            data.append("account", account)
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/delUser";
            axios.post(url, data).then((res) => {
                console.log("成功！");
                console.log(res);
                this.allUsers = res.data;
                this.showUsers = JSON.parse(JSON.stringify(this.allUsers));
                this.pagination();
            }).catch((err) => {
                console.log("失败！");
            });
        },
        searchUser: function () {
            if (this.searchContent !== "") {
                let data = new URLSearchParams();
                data.append("searchContent", this.searchContent)
                let url = "http://localhost:8080/bookstore_war_exploded/servlets/searchUser";
                axios.post(url, data).then((res) => {
                    console.log("成功！");
                    console.log(res);
                    this.showUsers = res.data;
                }).catch((err) => {
                    console.log("失败！");
                });
            } else {
                this.showUsers = JSON.parse(JSON.stringify(this.allUsers));
            }
            this.pagination();
        },
        //分页
        pagination: function () {
            // 根据后台数据的条数和每页显示数量算出一共几页,得0时设为1
            this.pageNum = Math.ceil(this.showUsers.length / this.pageSize) || 1;
            for (let i = 0; i < this.pageNum; i++) {
                // 每一页都是一个数组 形如 [['第一页的数据'],['第二页的数据'],['第三页数据']]
                // 根据每页显示数量 将后台的数据分割到 每一页,假设pageSize为5， 则第一页是1-5条，即slice(0,5)，第二页是6-10条，即slice(5,10)...
                this.totalPage[i] = this.showUsers.slice(this.pageSize * i, this.pageSize * (i + 1))
            }
            // 获取到数据后显示第一页内容
            this.showData = this.totalPage[this.currentPage];
            $(".page").eq(0).addClass("active");
            $(".page").eq(0).siblings().removeClass("active");
        },
        // 上一页
        prePage: function () {
            if (this.currentPage === 0)
                return;
            this.showData = this.totalPage[--this.currentPage];
            let ps = $(".page")
            for (let i = 0; i < ps.length; i++) {
                if (ps.eq(i).is(".active")) {
                    ps.eq(i - 1).addClass("active");
                    ps.eq(i - 1).siblings().removeClass("active");
                    break;
                }
            }
        },
        // 下一页
        nextPage: function () {
            if (this.currentPage === this.pageNum - 1)
                return;
            this.showData = this.totalPage[++this.currentPage];
            let ps = $(".page")
            for (let i = 0; i < ps.length; i++) {
                if (ps.eq(i).is(".active")) {
                    ps.eq(i + 1).addClass("active");
                    ps.eq(i + 1).siblings().removeClass("active");
                    break;
                }
            }
        },
        jumpPage: function (index) {
            this.showData = this.totalPage[index];
            let ps = $(".page");
            ps.eq(index).addClass("active");
            ps.eq(index).siblings().removeClass("active");
        },
    }

})