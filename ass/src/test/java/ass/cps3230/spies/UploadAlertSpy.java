package ass.cps3230.spies;

import ass.cps3230.util.UploadAlert;

public class UploadAlertSpy implements UploadAlert {

    public int numOfUploads = 0;

    @Override
    public void uploadAlert() {
        numOfUploads++;
    }

    @Override
    public void deleteAllAlerts() {
        numOfUploads = 0;
    }
}

