from signal import pause
from picamera import PiCamera
import datetime
import storeFileFB
from sense_hat import SenseHat

class Camera:
    def __init__(self):

        camera = PiCamera()

        sense = SenseHat()

        pressure=round(sense.pressure,2)
        humidity=round(sense.humidity,2)

        camera.start_preview()

        time = datetime.datetime.now().strftime("%H:%M:%S")

        loc = f'/home/pi/week10-lab1/images/frame{datetime.datetime.now()}.jpg'

        camera.capture(loc)


        storeFileFB.store_file(loc)
        storeFileFB.push_db(loc, time)

        print("Frame taken")

if __name__ == "__main__":
    camera = Camera()

