---
title: 个人项目 v1.0.0
language_tabs:
  - shell: Shell
  - http: HTTP
  - javascript: JavaScript
  - ruby: Ruby
  - python: Python
  - php: PHP
  - java: Java
  - go: Go
toc_footers: []
includes: []
search: true
code_clipboard: true
highlight_theme: darkula
headingLevel: 2
generator: "@tarslib/widdershins v4.0.17"

---

# 个人项目

> v1.0.0

Base URLs:

* <a href="http://prod-cn.your-api-server.com">正式环境: http://prod-cn.your-api-server.com</a>

# UserAPI文档/识别

## POST collect

POST /collect/add

> Body 请求参数

```json
{
  "collectName": "项目n",
  "caseName": "测试病例",
  "url": "http://www.google.com"
}
```

### 请求参数

| 名称       | 位置     | 类型     | 必选  | 说明   |
| -------- | ------ | ------ | --- | ---- |
| union_id | header | string | 否   | none |
| body     | body   | object | 否   | none |

> 返回示例

> 成功

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

```json
{
  "code": 0,
  "msg": "收藏失败",
  "data": null
}
```

### 返回结果

| 状态码 | 状态码含义                                                   | 说明  | 数据模型   |
| --- | ------------------------------------------------------- | --- | ------ |
| 200 | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | 成功  | Inline |

### 返回数据结构

## POST 添加历史

POST /history/add

> Body 请求参数

```json
{
  "url": "http://www.bing.com",
  "Case": "测试病例A"
}
```

### 请求参数

| 名称       | 位置     | 类型     | 必选  | 说明   |
| -------- | ------ | ------ | --- | ---- |
| union_id | header | string | 否   | none |
| body     | body   | object | 否   | none |

> 返回示例

> 成功

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

### 返回结果

| 状态码 | 状态码含义                                                   | 说明  | 数据模型   |
| --- | ------------------------------------------------------- | --- | ------ |
| 200 | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | 成功  | Inline |

### 返回数据结构

状态码 **200**

| 名称     | 类型      | 必选   | 约束   | 中文名 | 说明   |
| ------ | ------- | ---- | ---- | --- | ---- |
| » code | integer | true | none |     | none |
| » msg  | string  | true | none |     | none |
| » data | null    | true | none |     | none |

# UserAPI文档/收藏

## GET 字段搜索项目

GET /collect/seek

### 请求参数

| 名称            | 位置     | 类型     | 必选  | 说明   |
| ------------- | ------ | ------ | --- | ---- |
| seekStatement | query  | string | 否   | none |
| union_id      | header | string | 否   | none |

> 返回示例

> 成功

```json
{
  "code": 1,
  "msg": "success",
  "data": []
}
```

### 返回结果

| 状态码 | 状态码含义                                                   | 说明  | 数据模型   |
| --- | ------------------------------------------------------- | --- | ------ |
| 200 | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | 成功  | Inline |

### 返回数据结构

## POST 显示收藏数据

POST /collect/{num}

### 请求参数

| 名称       | 位置     | 类型     | 必选  | 说明   |
| -------- | ------ | ------ | --- | ---- |
| num      | path   | string | 是   | none |
| union_id | header | string | 否   | none |

> 返回示例

> 成功

```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
      "union_id": null,
      "projectID": "1",
      "url": "https://www.xm.com/",
      "collectName": "项目1",
      "caseName": "苹果黑星病",
      "date": "2023-07-01T09:11:17.000+00:00"
    },
    {
      "union_id": null,
      "projectID": "2",
      "url": "http://www.bidu.com/",
      "collectName": "项目a",
      "caseName": "病例a",
      "date": "2023-07-04T20:40:34.000+00:00"
    }
  ]
}
```

### 返回结果

| 状态码 | 状态码含义                                                   | 说明  | 数据模型   |
| --- | ------------------------------------------------------- | --- | ------ |
| 200 | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | 成功  | Inline |

### 返回数据结构

状态码 **200**

| 名称               | 类型       | 必选   | 约束   | 中文名  | 说明   |
| ---------------- | -------- | ---- | ---- | ---- | ---- |
| » projectCloumns | [object] | true | none |      | none |
| »» name          | string   | true | none | 项目名  | none |
| »» case          | string   | true | none | 病例名  | none |
| »» date-time     | string   | true | none | 收藏时间 | none |

## DELETE 删除收藏

DELETE /collect/delete/{projecID}

### 请求参数

| 名称       | 位置     | 类型     | 必选  | 说明   |
| -------- | ------ | ------ | --- | ---- |
| projecID | path   | string | 是   | none |
| union_id | header | string | 否   | none |

> 返回示例

> 成功

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

### 返回结果

| 状态码 | 状态码含义                                                   | 说明  | 数据模型   |
| --- | ------------------------------------------------------- | --- | ------ |
| 200 | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | 成功  | Inline |

### 返回数据结构

状态码 **200**

| 名称     | 类型      | 必选   | 约束   | 中文名 | 说明   |
| ------ | ------- | ---- | ---- | --- | ---- |
| » code | integer | true | none |     | none |
| » msg  | string  | true | none |     | none |
| » data | null    | true | none |     | none |

# UserAPI文档/首页

## GET LogIn

GET /UserLogIn

### 请求参数

| 名称       | 位置     | 类型     | 必选  | 说明   |
| -------- | ------ | ------ | --- | ---- |
| union_id | header | string | 否   | none |

> 返回示例

> 成功

### 返回结果

| 状态码 | 状态码含义                                                   | 说明  | 数据模型   |
| --- | ------------------------------------------------------- | --- | ------ |
| 200 | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | 成功  | Inline |

### 返回数据结构

# UserAPI文档/用户

## POST 下滑刷新

POST /history/{num}

### 请求参数

| 名称       | 位置     | 类型     | 必选  | 说明   |
| -------- | ------ | ------ | --- | ---- |
| num      | path   | string | 是   | none |
| union_id | header | string | 否   | none |

> 返回示例

> 成功

```json
{
  "code": 1,
  "msg": "success",
  "data": [
    {
      "union_id": null,
      "date": "2023-07-01T09:11:38.000+00:00",
      "url": "https://www.sm.com/",
      "case": "苹果黑星病"
    },
    {
      "union_id": null,
      "date": "2023-07-05T11:00:37.000+00:00",
      "url": "http://www.bing.com",
      "case": null
    },
    {
      "union_id": null,
      "date": "2023-07-05T11:50:06.000+00:00",
      "url": "http://www.bing.com",
      "case": null
    }
  ]
}
```

### 返回结果

| 状态码 | 状态码含义                                                   | 说明  | 数据模型   |
| --- | ------------------------------------------------------- | --- | ------ |
| 200 | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | 成功  | Inline |

### 返回数据结构

状态码 **200**

| 名称          | 类型          | 必选   | 约束   | 中文名 | 说明   |
| ----------- | ----------- | ---- | ---- | --- | ---- |
| » code      | integer     | true | none |     | none |
| » msg       | string      | true | none |     | none |
| » data      | [object]    | true | none |     | none |
| »» union_id | null        | true | none |     | none |
| »» date     | string      | true | none |     | none |
| »» url      | string      | true | none |     | none |
| »» case     | string¦null | true | none |     | none |

# 数据模型
