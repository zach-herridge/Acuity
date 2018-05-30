package com.acuitybotting.pathfinding.backend.services.tile_data.handler.aws;

import com.acuitybotting.pathfinding.backend.services.tile_data.domain.TileUpload;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;


/**
 * Created by Zachary Herridge on 5/29/2018.
 */
public class TileUploadHandler implements RequestHandler<TileUpload, String> {

    private final Gson gson = new Gson();

    @Override
    public String handleRequest(TileUpload tileUpload, Context context) {
        String json = gson.toJson(tileUpload);
        return "Sec: ";
    }
}
