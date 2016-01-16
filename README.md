java-vision-gui
===============

An interactive GUI and module framework for OpenCV image processing vision module development in Java, inspired by [CUAUV](https://github.com/cuauv)'s auv-vision-gui.  

## Features
- Reading frames from cameras, videos, and images
- Automatic resizing of capture source frames to desired size
- Mapping capture sources to vision modules for processing
- Displaying images posted by vision modules onto the GUI
- Displaying arbitrary text associated with images ("tags") posted by vision modules onto the GUI
- Dynamic control of vision module variables through the GUI
    - Automatic generation of sliders for integer and floating point variables declared in vision modules
    - Automatic generation of checkboxes for boolean variables declared in vision modules

## Usage
Vision modules may be implemented using OpenCV's Java bindings and stored in the `modules` package. To configure a capture source for your vision module, add a mapping in `src/modules/VisionModuleSuite.java` from your capture source to your vision module, as demonstrated in the included example.

## Screenshots

![Minions Banana Example](/images/minions-banana-screenshot.png)
