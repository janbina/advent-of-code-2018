package main

import (
	"aoc19/utils"
	"fmt"
	"math"
	"strings"
)

func main() {
	mat, springX := getInput()
	fallDown(springX, 0, mat)

	cntRest, cntFlow := 0, 0
	for _, r := range mat {
		for _, x := range r {
			if x == REST_W {
				cntRest++
			} else if x == FLOW_W {
				cntFlow++
			}
		}
	}
	fmt.Println(cntRest+cntFlow, cntRest)
}

const SAND = '.'
const CLAY = '#'
const REST_W = '~'
const FLOW_W = '|'

func getInput() ([][]byte, int) {
	lines := utils.ReadLines("input.txt")
	var coords [][4]int
	for _, line := range lines {
		seg := strings.Split(line, ",")
		n1 := utils.ToInt(strings.Split(seg[0], "=")[1])
		n23 := strings.Split(strings.Split(seg[1], "=")[1], "..")
		n2, n3 := utils.ToInt(n23[0]), utils.ToInt(n23[1])
		if line[0] == 'x' {
			coords = append(coords, [4]int{n1, n1, n2, n3})
		} else {
			coords = append(coords, [4]int{n2, n3, n1, n1})
		}
	}

	xMin, xMax, yMin, yMax := math.MaxInt32, 0, math.MaxInt32, 0
	for _, l := range coords {
		xMin = utils.Min(xMin, l[0])
		xMax = utils.Max(xMax, l[1])
		yMin = utils.Min(yMin, l[2])
		yMax = utils.Max(yMax, l[3])
	}
	xLen, yLen := xMax-xMin+3, yMax-yMin+1
	mat := make([][]byte, yLen)
	for i := range mat {
		mat[i] = make([]byte, xLen)
		for j := range mat[i] {
			mat[i][j] = SAND
		}
	}

	for _, c := range coords {
		for i := c[2] - yMin; i <= c[3]-yMin; i++ {
			for j := c[0] - xMin + 1; j <= c[1]-xMin+1; j++ {
				mat[i][j] = CLAY
			}
		}
	}

	return mat, 500 - xMin + 1
}

func printMap(mat [][]byte) {
	for _, x := range mat {
		fmt.Println(string(x))
	}
}

func fallDown(x int, y int, mat [][]byte) {
	for i := y; i < len(mat); i++ {
		if mat[i][x] == CLAY || mat[i][x] == REST_W {
			flowHorizontally(x, i-1, mat)
			break
		} else if mat[i][x] == FLOW_W {
			break
		}
		mat[i][x] = FLOW_W
	}
}

func flowHorizontally(x int, y int, mat [][]byte) {
	leftEdge, rightEdge := -1, -1
	for j := x; j < len(mat[y]); j++ {
		if mat[y][j] == CLAY {
			rightEdge = j
			break
		} else if mat[y+1][j] == SAND {
			fallDown(j, y, mat)
			break
		} else if mat[y+1][j] == FLOW_W || mat[y][j] == REST_W {
			break
		}
		mat[y][j] = FLOW_W
	}

	for j := x; j >= 0; j-- {
		if mat[y][j] == CLAY {
			leftEdge = j
			break
		} else if mat[y+1][j] == SAND {
			fallDown(j, y, mat)
			break
		} else if mat[y+1][j] == FLOW_W || mat[y][j] == REST_W {
			break
		}
		mat[y][j] = FLOW_W
	}

	if leftEdge >= 0 && rightEdge >= 0 {
		for j := leftEdge + 1; j < rightEdge; j++ {
			mat[y][j] = REST_W
		}
		flowHorizontally(x, y-1, mat)
	}
}
