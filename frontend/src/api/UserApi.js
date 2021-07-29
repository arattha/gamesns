/*
User API 예시
 */
import http from '@/util/http-common';

const requestkakaoLogin = (data, callback, errorCallback) => {
  //백앤드와 로그인 통신하는 부분
  // callback();
  console.log(data);
  http
    .get('/kakaoLogin', { params: { code: data } })
    .then((res) => {
      // alert("로그인 되었습니다.");
      console.log(res);
      callback(res.data);
    })
    .catch((err) => {
      console.log(err);
      if (!err.response) {
        errorCallback(true);
      } else {
        errorCallback();
      }
    });
};

const requestExistUser = (data, callback, errorCallback) => {
  http
    .get(`/existUser/${data}`)
    .then((res) => {
      // alert("로그인 되었습니다.");
      console.log(res);
      callback(res.data);
    })
    .catch((err) => {
      console.log(err);
      errorCallback();
    });
};

const requestLogin = (data, callback, errorCallback) => {
  http
    .post('/auth/login', data)
    .then((res) => {
      console.log(res.data);
      callback(res.data);
    })
    .catch((err) => {
      console.log(err);
      errorCallback();
    });
};

const requestDupCheck = (data, callback, errorCallback) => {
  console.log(data);
  http
    .get('/dupcheck', { params: { nickname: data } })
    .then(() => {
      alert('사용 가능한 닉네임입니다.');
      callback();
    })
    .catch(() => {
      alert('사용 중인 닉네임입니다.');
      errorCallback();
    });
};

const requestSignUp = (data, callback, errorCallback) => {
  http
    .post('/auth/signup', data)
    .then((res) => {
      // alert('회원가입에 성공했습니다.');
      callback(res.data);
    })
    .catch(() => {
      alert('회원가입에 실패했습니다.');
      errorCallback();
    });
};

const logout = (data, callback, errorCallback) => {
  http
    .get('/kakaoLogout', { params: data })
    .then(() => {
      alert('로그아웃!');
      callback();
    })
    .catch((err) => {
      alert('실패!');
      errorCallback();
    });
};

const requestFollowing = (data, callback, errorCallback) => {
  console.log('userapi', data);
  http
    .get('/follow/following', { params: data })
    .then((list) => {
      console.log('list', list);
      callback(list);
    })
    .catch(() => {
      alert('팔로잉 가져오기 실패!');
      errorCallback();
    });
};

const requestFollower = (data, callback, errorCallback) => {
  http
    .get('/follow/follower', { params: data })
    .then((list) => {
      callback(list);
    })
    .catch((err) => {
      alert('팔로워 가져오기 실패!');
      errorCallback();
    });
};

const requestFollowUpdate = (data, callback, errorCallback) => {
  console.log('data', data);

  let data2 = {
    fromNickname: data.fromNickname,
    toNickname: data.toNickname,
  };

  if (data.type) {
    http
      .post('/follow/AddOrDeleteFollowing', data2)
      .then((res) => {
        callback(res.data);
      })
      .catch(() => {
        errorCallback();
      });
  } else {
    http
      .post('/follow/AddOrDeleteFollower', data2)
      .then((res) => {
        callback(res.data);
      })
      .catch(() => {
        errorCallback();
      });
  }
};

const requestUpdateUser = (formData, callback, errorCallback) => {
  http
    .put('/account/mypageUpdate', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })
    .then((res) => {
      callback(res);
    })
    .catch(() => {
      errorCallback();
    });
};

const requestGetUser = (data, callback, errorCallback) => {
  console.log(data);
  http
    .get('/info/' + data)
    .then((res) => {
      callback(res);
    })
    .catch(() => {
      errorCallback();
    });
};

const UserApi = {
  requestkakaoLogin: (data, callback, errorCallback) =>
    requestkakaoLogin(data, callback, errorCallback),
  requestSignUp: (data, callback, errorCallback) => requestSignUp(data, callback, errorCallback),
  logout: (data, callback, errorCallback) => logout(data, callback, errorCallback),
  requestFollowing: (data, callback, errorCallback) =>
    requestFollowing(data, callback, errorCallback),
  requestFollower: (data, callback, errorCallback) =>
    requestFollower(data, callback, errorCallback),
  requestDupCheck: (data, callback, errorCallback) =>
    requestDupCheck(data, callback, errorCallback),
  requestUpdateUser: (data, callback, errorCallback) =>
    requestUpdateUser(data, callback, errorCallback),
  requestFollowUpdate: (data, callback, errorCallback) =>
    requestFollowUpdate(data, callback, errorCallback),
  requestExistUser: (data, callback, errorCallback) =>
    requestExistUser(data, callback, errorCallback),
  requestLogin: (data, callback, errorCallback) => requestLogin(data, callback, errorCallback),
  requestGetUser: (data, callback, errorCallback) => requestGetUser(data, callback, errorCallback),
};

export default UserApi;
