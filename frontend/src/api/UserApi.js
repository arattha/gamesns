/*
 User API 예시
 */
import http from "@/util/http-common";

const requestLogin = (data,callback,errorCallback) => {
    //백앤드와 로그인 통신하는 부분
    // callback();
    console.log(data);
    http
        .get("/account/login", {params:data})
        .then(() => {
            alert("로그인 되었습니다.");
            callback();
        })
        .catch(() => {
            alert("아이디나 비밀번호가 맞지 않습니다.");
            errorCallback();
        })
}

const requestSignUp = (data,callback,errorCallback) => {
    http
        .post("/account/signup", data)
        .then(() => {
            alert("회원가입에 성공했습니다.");
            callback();
        })
        .catch(() => {
            alert("회원가입에 실패했습니다.");
            errorCallback();
        })
}

const UserApi = {
    requestLogin:(data,callback,errorCallback)=>requestLogin(data,callback,errorCallback),
    requestSignUp:(data,callback,errorCallback)=>requestSignUp(data,callback,errorCallback)
}

export default UserApi
