# ImageSmash (Context Aware Image Resizing)

This is a Java implementation of image seam carving. Energy values are
calculated at each pixel and the path of least energy-connected pixel is
removed. The output is a resized image that has lost minimal to no content.
Located below are some images that have been resized using this method. Due to
the nature of this algorithm, resizing too much in any direction will result in image distortion.

## Original

![Mountain](./image_folder/m.jpg)

![People](./image_folder/p.jpg)

![Snowboard](./image_folder/s.jpg)

![River](./image_folder/w.jpg)

## Resized

![Mountain](./image_folder/m.jpg_20_100.jpg)
![Mountain](./image_folder/m.jpg_100_20.jpg)
![Mountain](./image_folder/m.jpg_100_100.jpg)

![People](./image_folder/p.jpg_200_20.jpg)
![People](./image_folder/p.jpg_100_100.jpg)

![Snowboard](./image_folder/s.jpg_200_0.jpg)
![Snowboard](./image_folder/s.jpg_200_20.jpg)

![River](./image_folder/w.jpg_100_0.jpg)
![River](./image_folder/w.jpg_100_50.jpg)
![River](./image_folder/w.jpg_200_0.jpg)