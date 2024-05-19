package com.example.demo.entity;

import java.util.List;

public class PerfectArtifact {
    Artifact artifact;
    List<Artifact> relatedArtifact;
    public PerfectArtifact(){
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }

    public List<Artifact> getRelatedArtifact() {
        return relatedArtifact;
    }

    public void setRelatedArtifact(List<Artifact> relatedArtifact) {
        this.relatedArtifact = relatedArtifact;
    }

    @Override
    public String toString() {
        return "PerfectArtifact{" +
                "artifact=" + artifact +
                ", relatedArtifact=" + relatedArtifact +
                '}';
    }
}
