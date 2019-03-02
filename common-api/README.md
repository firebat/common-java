## Json

    JsonMapper mapper = MapperBuilder.getDefaultMapper();
    mapper.writeValueAsString(object);
    mapper.readValue("{...}", clz-or-typereference);

## Query
数据查询参数封装

    Query query = new GenericQuery()
            .fill("name", "alice")
            .fill("city", "beijing")
            .like("name")
            .orderBy("distance:desc")
            .limit(10)
            .offset(20);
