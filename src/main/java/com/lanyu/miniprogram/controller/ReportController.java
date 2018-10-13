package com.lanyu.miniprogram.controller;

import com.lanyu.miniprogram.dto.SingleResultResponse;
import com.lanyu.miniprogram.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author i343746
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @ResponseBody
    @RequestMapping(path = "/getReportCount", method = RequestMethod.GET)
    public SingleResultResponse getReportCountByCompositeKey(
            @RequestParam(required = true) String wechatId,
            @RequestParam(required = true) String timestamp
    ) {
        return new SingleResultResponse(1);
    }

    @ResponseBody
    @RequestMapping(path = "/getReport", method = RequestMethod.GET)
    public SingleResultResponse getReportByCompositeKey(
            @RequestParam(required = true) String wechatId,
            @RequestParam(required = true) String timestamp
    ) {
        return new SingleResultResponse(1);
    }

    @ResponseBody
    @RequestMapping(path = "/getReportData", method = RequestMethod.GET)
    public SingleResultResponse getReportDataByCompositeKey(
            @RequestParam(required = true) String wechatId,
            @RequestParam(required = true) String timestamp
    ) {
        return new SingleResultResponse(1);
    }

    @ResponseBody
    @RequestMapping(path = "/getReportDataList", method = RequestMethod.GET)
    public SingleResultResponse getReportDataListWithPagination(
            @RequestParam(required = true) String wechatId,
            @RequestParam(required = true) String top,
            @RequestParam(required = true) String skip
    ) {
        return new SingleResultResponse(1);
    }

    @ResponseBody
    @RequestMapping(path = "/generateReport", method = RequestMethod.POST)
    public SingleResultResponse generateReport() {
        return new SingleResultResponse(1);
    }
}
