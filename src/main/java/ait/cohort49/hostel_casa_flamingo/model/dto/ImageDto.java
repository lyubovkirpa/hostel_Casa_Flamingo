package ait.cohort49.hostel_casa_flamingo.model.dto;

import java.util.Objects;


public class ImageDto {

    private Long id;
    private String s3Path;
    private String fileOriginName;
    private String s3BucketName;
    private Long bedId;
    private Long roomId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getS3Path() {
        return s3Path;
    }

    public void setS3Path(String s3Path) {
        this.s3Path = s3Path;
    }

    public String getFileOriginName() {
        return fileOriginName;
    }

    public void setFileOriginName(String fileOriginName) {
        this.fileOriginName = fileOriginName;
    }

    public String getS3BucketName() {
        return s3BucketName;
    }

    public void setS3BucketName(String s3BucketName) {
        this.s3BucketName = s3BucketName;
    }

    public Long getBedId() {
        return bedId;
    }

    public void setBedId(Long bedId) {
        this.bedId = bedId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageDto imageDto = (ImageDto) o;
        return Objects.equals(id, imageDto.id) && Objects.equals(s3Path, imageDto.s3Path) && Objects.equals(fileOriginName, imageDto.fileOriginName) && Objects.equals(s3BucketName, imageDto.s3BucketName) && Objects.equals(bedId, imageDto.bedId) && Objects.equals(roomId, imageDto.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, s3Path, fileOriginName, s3BucketName, bedId, roomId);
    }

    @Override
    public String toString() {
        return "ImageDto{" +
                "id=" + id +
                ", s3Path='" + s3Path + '\'' +
                ", fileOriginName='" + fileOriginName + '\'' +
                ", s3BucketName='" + s3BucketName + '\'' +
                ", bedId=" + bedId +
                ", roomId=" + roomId +
                '}';
    }
}
