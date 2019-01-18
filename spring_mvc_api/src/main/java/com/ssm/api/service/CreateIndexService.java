package com.ssm.api.service;

import com.ssm.api.model.FileModel;

import java.io.IOException;
import java.util.List;

/**
 * Project Name: even_web
 * Des:
 * Created by Even on 2019/1/16
 */
public interface CreateIndexService {

    List<FileModel> searchIndex(String key) throws IOException;
}
