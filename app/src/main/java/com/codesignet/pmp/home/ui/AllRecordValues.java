package com.codesignet.pmp.home.ui;

public class AllRecordValues {
    private String RecordName;
    private int RecordLevel;

    public AllRecordValues(String initiating_process_group, Integer integer) {
        setRecordLevel(integer);
        setRecordName(initiating_process_group);
    }

    public String getRecordName() {
        return RecordName;
    }

    public void setRecordName(String recordName) {
        RecordName = recordName;
    }

    public int getRecordLevel() {
        return RecordLevel;
    }

    public void setRecordLevel(int recordLevel) {
        RecordLevel = recordLevel;
    }
}
