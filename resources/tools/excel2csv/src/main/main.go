package main

import (
	gui2 "gui"
)

func main() {
	gui := gui2.Instance()
	gui.Init()
	gui.Run()
}
