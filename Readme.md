### 接口：129.28.65.198:8080

URL: /user/enroll  

method: POST 

Param: name,password 

#注册过后会给出一个id

URL: /user/login

method: POST

Param: id,password



URL: /user/login

method: GET

return: 返回登陆视图



可以进行大厅聊天，把登陆的用户名以及session 放在一个map里面



先通过/user/login 登陆后 会进入 页面 选择你想加入的房间，然后  点击准备 

就可以 。。。下棋了

运行环境（mysql）

bug肯定很多，饶命