package utils.result.code;

public interface Code {
	// 成功
	int SUCCESS  = Errs.put(200, "成功");
	// 操作有误
	int ERROR	 = Errs.put(-1, "操作有误");
	// 未知错误
	int LOGIN_OUT	 = Errs.put(-2, "登录失效");
	//登录成功
	int LOGIN_SUCCESS  = Errs.put(201, "登录成功");
}
