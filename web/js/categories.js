let vm = new Vue({
    el: "#app",
    data: {
        categories:"",
    },
    mounted: function () {
        this.getCategories();
    },
    methods: {
        getCategories: function () {
            //axios
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/getCategories";
            axios.post(url).then((res) => {
                console.log("成功！");
                console.log(res);
                this.categories = res.data;
                console.log(this.categories)
            }).catch((err) => {
                console.log("失败！");
            });
        },
        toCategoryDetail:function (index) {
            window.location.href = "categoryDetail.html?id="+index;
        },
    }
})