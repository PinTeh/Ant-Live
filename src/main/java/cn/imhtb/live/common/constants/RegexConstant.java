package cn.imhtb.live.common.constants;

/**
 * @author Charles7c
 * @date 2023/1/10 20:06
 */
public class RegexConstant {

    /**
     * 用户名正则（用户名长度为 4-64 个字符，支持大小写字母、数字、下划线，以字母开头）
     */
    public static final String USERNAME = "^[a-zA-Z][a-zA-Z0-9_]{3,64}$";

    /**
     * 密码正则模板（密码长度为 min-max 个字符，支持大小写字母、数字、特殊字符，至少包含字母和数字）
     */
    public static final String PASSWORD_TEMPLATE = "^(?=.*\\d)(?=.*[a-z]).{%s,%s}$";

    /**
     * 密码正则（密码长度为 8-32 个字符，支持大小写字母、数字、特殊字符，至少包含字母和数字）
     */
    public static final String PASSWORD = "^(?=.*\\d)(?=.*[a-z]).{8,32}$";

    /**
     * 特殊字符正则
     */
    public static final String SPECIAL_CHARACTER = "[-_`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\\n|\\r|\\t";

    /**
     * 通用编码正则（长度为 2-30 个字符，支持大小写字母、数字、下划线，以字母开头）
     */
    public static final String GENERAL_CODE = "^[a-zA-Z][a-zA-Z0-9_]{1,29}$";

    /**
     * 通用名称正则（长度为 2-30 个字符，支持中文、字母、数字、下划线，短横线）
     */
    public static final String GENERAL_NAME = "^[\\u4e00-\\u9fa5a-zA-Z0-9_-]{2,30}$";

    /**
     * 包名正则（可以包含大小写字母、数字、下划线，每一级包名不能以数字开头）
     */
    public static final String PACKAGE_NAME = "^(?:[a-zA-Z_][a-zA-Z0-9_]*\\.)*[a-zA-Z_][a-zA-Z0-9_]*$";

}
