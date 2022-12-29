import camera
import ts


if __name__ == "__main__":
    camera = camera.Camera()
    ts = ts.ThingPub()
    ts.run()