package com.lanyu.miniprogram.controller;

import com.google.gson.*;
import com.lanyu.miniprogram.bean.RenderData;
import com.lanyu.miniprogram.dto.RenderDataDTO;
import com.lanyu.miniprogram.dto.SingleResultResponse;
import com.lanyu.miniprogram.service.RenderDataAdapterService;
import com.lanyu.miniprogram.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 业务逻辑：
 * 1. 第一次生成
 *      小程序端 简报页面，点击生成按钮
 *      1.1 小程序调用 Details.getExpressData() & Details.getDetailedData() 获取值
 *      1.2 拼接成一个object，并parser成json字符串传递到后端  api: /report/generateReport
 *      后端
 *      1.3 后端将render值存数据库，并将render结果转成可存放于数据库的字段
 *
 * 2. 查看图片
 *
 *
 *
 * @author i343746
 */
@RestController
@RequestMapping("/report")
public class ReportController {
//    @Autowired
//    private ReportService reportService;

    @Autowired
    RenderDataAdapterService adapterService;

    /**
     * @param wechatId
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/getReportCount", method = RequestMethod.GET)
    public SingleResultResponse getReportCountByCompositeKey(
            @RequestParam(required = true) String wechatId
    ) {
        return new SingleResultResponse(1);
    }

    /**
     * 凭借id与时间戳获取图片
     * @param wechatId
     * @param timestamp
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/getReport", method = RequestMethod.GET)
    public SingleResultResponse getReportByCompositeKey(
            @RequestParam(required = true) String wechatId,
            @RequestParam(required = true) String timestamp
    ) {
        return new SingleResultResponse(1);
    }

    /**
     * 根据id与timestamp 获取某个特定的ReportData
     * @param wechatId
     * @param timestamp
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/getReportData", method = RequestMethod.GET)
    public SingleResultResponse getReportDataByCompositeKey(
            @RequestParam(required = true) String wechatId,
            @RequestParam(required = true) String timestamp
    ) {
        return new SingleResultResponse(1);
    }

    /**
     * 根据分页获取若干个ReportData
     * @param wechatId
     * @param top
     * @param skip
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/getReportDataList", method = RequestMethod.GET)
    public SingleResultResponse getReportDataListWithPagination(
            @RequestParam(required = true) String wechatId,
            @RequestParam(required = true) String top,
            @RequestParam(required = true) String skip
    ) {
        return new SingleResultResponse(1);
    }

    /**
     * payload是render数据，将render数据 和 render 图片结果存入数据库
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/generateReport", method = RequestMethod.POST)
    public SingleResultResponse generateReport(
            @RequestBody String json
    ) {
        Gson gson = new GsonBuilder().registerTypeAdapter(List.class, new JsonDeserializer<List<?>>() {
            @Override
            public List<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                ArrayList<Integer> list = new ArrayList<>();
                for (JsonElement e: json.getAsJsonArray()) {
                    list.add(e.getAsInt());
                }
                return list;
            }
        }).create();

        RenderDataDTO renderDataDTO = gson.fromJson(json, RenderDataDTO.class);
        RenderData renderData = adapterService.getDataThatStoredInMysql(renderDataDTO);
        System.out.println(renderData);

        return new SingleResultResponse(adapterService.getDataThatCanBeConvertToJson(renderData));
    }
}
