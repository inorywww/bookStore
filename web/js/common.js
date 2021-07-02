function getCok(cookie_name) {
    let allcookies = document.cookie;
    let cookie_pos = allcookies.indexOf(cookie_name);   //索引的长度
    // 如果找到了索引，就代表cookie存在，反之，就说明不存在。
    let value = "";
    if (cookie_pos !== -1) {
        cookie_pos += cookie_name.length + 1;
        let cookie_end = allcookies.indexOf(";", cookie_pos);
        if (cookie_end === -1) {
            cookie_end = allcookies.length;
        }
        value = unescape(allcookies.substring(cookie_pos, cookie_end));
    }
    return value;
}

function addCart(id) {
    let r = "未登录";
    let account = getCok("account");
    if (account !== "") {
        let data = new URLSearchParams();
        data.append('id', id)
        //axios
        let url = "http://localhost:8080/bookstore_war_exploded/servlets/addToCart";
        axios.post(url, data).then((res) => {
            console.log("成功！");
            console.log(res);
            r = "success";
        }).catch((err) => {
            console.log("失败！");
            r = "error"
        });
    }
    return r;
}