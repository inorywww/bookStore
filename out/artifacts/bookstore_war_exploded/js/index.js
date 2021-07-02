let vm = new Vue({
    el: "#app",
    data(){
        return{
            allBooks: {},
            showData: {},

        }
    },
    mounted: function () {
        this.getBookInfo();
    },
    methods: {
        getBookInfo: function () {
            //axios
            console.log("getBookInfo");
            let url = "http://localhost:8080/bookstore_war_exploded/servlets/getBookInfo";
            axios.post(url).then((res) => {
                console.log("成功！");
                console.log(res);
                this.allBooks = res.data;
                this.showData = JSON.parse(JSON.stringify(this.allBooks))
                for (let i = 0; i < this.showData.length; i++) {
                    this.showData[i].contents = this.showData[i].contents.slice(0, 4)
                }
            }).catch((err) => {
                console.log("失败！");
            });
        },
        moreInfo: function (index, index1) {
            let id = this.showData[index].contents[index1].id;
            console.log("id:" + id)
            window.location.href = "bookDetail.html?id=" + id;
        },
        moreBook: function (index) {
            let books = this.allBooks[index];
            console.log(books);
            window.location.href = "categoryDetail.html?index=" + index;
        },
        addToCart: function (index, index1) {
            let account = getCok("account");
            if (account !== "") {
                let id = this.showData[index].contents[index1].id;
                addCart(id);
                $(".control-label").text('加入购物车成功！');
                let options = {
                    "backdrop" : "true"
                };
                $('#myModal').modal(options);
            }
            else {
                $(".control-label").text('未登录，请登录后重试！');
                let options = {
                    "backdrop" : "true"
                };
                $('#myModal').modal(options);
            }
        },

    }
})