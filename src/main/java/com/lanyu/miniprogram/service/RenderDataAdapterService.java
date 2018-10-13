package com.lanyu.miniprogram.service;

import com.google.gson.*;
import com.lanyu.miniprogram.bean.RenderData;
import com.lanyu.miniprogram.bean.User;
import com.lanyu.miniprogram.dto.RenderDataDTO;
import com.lanyu.miniprogram.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author i343746
 */
@Service
public class RenderDataAdapterService {

    @Autowired
    UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(RenderDataAdapterService.class);

    public RenderDataDTO inflateData(RenderDataDTO dataDTO){
        dataDTO = this.getDataThatCanBeConvertToJson(this.getDataThatStoredInMysql(dataDTO));

        dataDTO.setGender(dataDTO.getGender().equals("男")? "先生": "女士");
        dataDTO.setRatioOfBasicReceivePension(new Double(dataDTO.getRatioOfBasicReceivePension() * 100).intValue());
        double point = dataDTO.getPointAverage();
        DecimalFormat df = new DecimalFormat("0.00");
        String sPoint = df.format(point);
        dataDTO.setPointAverage(Double.parseDouble(sPoint));
        dataDTO.setRetirementSalaryPerMonth(new Double(dataDTO.getRetirementSalaryPerMonth()).intValue());
        dataDTO.setPensionBasicSocialInsurance(new Double(dataDTO.getPensionBasicSocialInsurance()).intValue());
        dataDTO.setPensionPersonalAccount(new Double(dataDTO.getPensionPersonalAccount()).intValue());
        dataDTO.setPensionTransition(new Double(dataDTO.getPensionTransition()).intValue());
        dataDTO.setCompanyAnnuity(new Double(dataDTO.getCompanyAnnuity()).intValue());
        dataDTO.setPensionInFirstRetirementMonth(new Double(dataDTO.getPensionInFirstRetirementMonth()).intValue());

        dataDTO.setPensionReplacementRate(new Double(dataDTO.getPensionReplacementRate() * 100).intValue());
        dataDTO.setRateOfSocialInsurancePlusAnnuity(new Double(dataDTO.getRateOfSocialInsurancePlusAnnuity() * 100).intValue());
        dataDTO.setGapOfPensionReplacementRateValue(new Double(dataDTO.getGapOfPensionReplacementRateValue() * 100).intValue());

        dataDTO.setPensionGapPerMonth(new Double(dataDTO.getPensionGapPerMonth()).intValue());

        List<Integer> xArray = new ArrayList<>();
        int totalLength = dataDTO.getSalaries().size() + dataDTO.getPensions().size();
        for (int i = dataDTO.getAge(); i < totalLength + dataDTO.getAge(); i++) {
            xArray.add(i);
        }
        dataDTO.setxArray(xArray);

        List<Integer> salaries = dataDTO.getSalaries();
        int salarySize = salaries.size();
        for (int i = 0; i < dataDTO.getPensions().size(); i++) {
            salaries.add(0);
        }

        List<Integer> pensions = dataDTO.getPensions();
        List<Integer> gaps = dataDTO.getGaps();
        for (int i = 0; i < salarySize; i++) {
            pensions.add(0);
            gaps.add(0);
        }

        Collections.sort(pensions);
        Collections.sort(gaps);
        System.out.println(dataDTO.getWechatId());
        User user = userRepository.findByWechatId(dataDTO.getWechatId());
        dataDTO.setManager_name(user.getName());
        dataDTO.setEnterprise(user.getEnterprise());
        dataDTO.setEnterprise_branch(user.getEnterpriseBranch());
        dataDTO.setPhone(user.getPhone());
        dataDTO.setTitle(user.getTitle());

        return dataDTO;
    }

    public RenderData getDataThatStoredInMysql(RenderDataDTO dto){
        Gson gson = new GsonBuilder().registerTypeAdapter(List.class, new JsonSerializer<List<?>>() {
            @Override
            public JsonElement serialize(List<?> src, Type typeOfSrc, JsonSerializationContext context) {
                Gson arrGson = new Gson();
                return new JsonPrimitive(arrGson.toJson(src));
            }
        }).create();
        String dtoJson = gson.toJson(dto);

        return gson.fromJson(dtoJson, RenderData.class);
    }

    public RenderDataDTO getDataThatCanBeConvertToJson(RenderData data){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(List.class, new JsonDeserializer<List<Integer>>() {
                    @Override
                    public List<Integer> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        String arrString = json.getAsString();
                        Gson arrGson = new Gson();
                        List<Double> list = arrGson.fromJson(arrString, List.class);
                        List<Integer> tar = new ArrayList<>();
                        for (Double a :
                                list) {
                            tar.add(a.intValue());
                        }
                        return tar;
                }}).create();
        String renderJson = gson.toJson(data);
        RenderDataDTO dto = gson.fromJson(renderJson, RenderDataDTO.class);
        return dto;
    }

    public String saveInDatabase(RenderData data){
        return null;
    }

    public RenderData getDataFromDatabase(){
        return null;
    }
}
