package com.dafang.monitor.nx.statistics.controller;

import com.dafang.monitor.nx.config.Apicp;
import com.dafang.monitor.nx.statistics.entity.dto.ResuleDto;
import com.dafang.monitor.nx.statistics.entity.po.DailyParam;
import com.dafang.monitor.nx.statistics.entity.vo.PeriodStas;
import com.dafang.monitor.nx.statistics.service.StasService;
import com.dafang.monitor.nx.utils.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: echo
 * @createDate: 2020/4/13
 * @version: 1.0
 */
@RestController
@Api(value = "echo",tags = "{站数处理}")
@RequestMapping("stas/")
public class StasController {
    @Autowired
    private StasService service;
    @PostMapping(value = "period")
    @ApiOperation(value = "同期站数数数据查询",notes = "同期站数数")
    public ResuleDto<Object> periodStas(@Apicp("regions,startDate,endDate,climateScale," +
            "element,min,max,code,rankStartYear,rankEndYear") @RequestBody DailyParam params){
        ResuleDto<Object> resuleDto = new ResuleDto<>();
        params.setST(params.getStartDate().substring(4));
        params.setET(params.getEndDate().substring(4));
        // 默认直接查询全区的数据
        params.setCondition(CommonUtils.getCondition("1"));
        List<PeriodStas> periodList = service.periodSta(params);
        check(resuleDto, periodList);
        return resuleDto;
    }
    // 数据校验
    public <T> void check(ResuleDto<Object> resuleDto,List<T> list){
        resuleDto.setRespData(list);
        if (list.size()>0){
            resuleDto.setRespCode(0);
            resuleDto.setMessage("该条件下无数据");
        }
    }
}
