package com.avito.emcee.queue

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
public data class BuildArtifacts(
    val app: BuildArtifact,
    val testApp: BuildArtifact
)
