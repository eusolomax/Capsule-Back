package com.capsule.capsule.mapper;

import org.mapstruct.Mapper;

import com.capsule.capsule.dtos.request.CreateTrackRequest;
import com.capsule.capsule.dtos.response.TrackResponse;
import com.capsule.capsule.entities.Track;

@Mapper(componentModel = "spring")
public interface TrackMapper {

    TrackResponse toResponse(Track track);

    Track toEntity(CreateTrackRequest request);

}