package com.lanyu.miniprogram.service;

import com.google.gson.*;
import com.lanyu.miniprogram.bean.RenderData;
import com.lanyu.miniprogram.dto.RenderDataDTO;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author i343746
 */
@Service
public class RenderDataAdapterService {

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
