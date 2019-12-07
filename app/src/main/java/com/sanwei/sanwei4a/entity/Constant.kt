package com.sanwei.sanwei4a.entity

object Constant {
    /* ---------------状态码---------------*/
    val OK_CODE = 200
    val FAIL_CODE = 300

    val NOT_FOUND = 404
    val EXCEPTION = 500

    /* ---------------订单状态---------------*/
    /** 未付款 */
    val ORDER_STAT_UNPAY = 1
    /** 已付款 待收货 */
    val ORDER_STAT_UNCONFIRM = 2
    /** 已收货 待评价 */
    val ORDER_STAT_UNCOMMENT = 3
    /** 已完成 */
    val ORDER_STAT_FINISHED = 4

    /* ---------------支付方式---------------*/
    /** 积分支付*/
    val PAYTYPE_POINTS = 1
    /** 押金*/
    val PAYTYPE_DEPOSIT = 2

    /* ---------------书籍状态---------------*/
    /** 待审核 */
    var BOO_STAT_UNCONFIRM = 4
    /** 可借 */
    var BOO_STAT_ONSHELF = 1
    /** 被借 */
    var BOO_STAT_BUSY = 2
    /** 报废 不再可用 */
    var BOO_STAT_USELESS = 3
    /** 被移除 */
    var BOO_STAT_REMOVED = 5

}