/* WARNING: This is auto-generated file. Do not modify, since changes will
 * be lost! Modify the generating script instead.
 */
virtual void				destroyInstance										(VkInstance instance, const VkAllocationCallbacks* pAllocator) const;
virtual VkResult			enumeratePhysicalDevices							(VkInstance instance, deUint32* pPhysicalDeviceCount, VkPhysicalDevice* pPhysicalDevices) const;
virtual void				getPhysicalDeviceFeatures							(VkPhysicalDevice physicalDevice, VkPhysicalDeviceFeatures* pFeatures) const;
virtual void				getPhysicalDeviceFormatProperties					(VkPhysicalDevice physicalDevice, VkFormat format, VkFormatProperties* pFormatProperties) const;
virtual VkResult			getPhysicalDeviceImageFormatProperties				(VkPhysicalDevice physicalDevice, VkFormat format, VkImageType type, VkImageTiling tiling, VkImageUsageFlags usage, VkImageCreateFlags flags, VkImageFormatProperties* pImageFormatProperties) const;
virtual void				getPhysicalDeviceProperties							(VkPhysicalDevice physicalDevice, VkPhysicalDeviceProperties* pProperties) const;
virtual void				getPhysicalDeviceQueueFamilyProperties				(VkPhysicalDevice physicalDevice, deUint32* pQueueFamilyPropertyCount, VkQueueFamilyProperties* pQueueFamilyProperties) const;
virtual void				getPhysicalDeviceMemoryProperties					(VkPhysicalDevice physicalDevice, VkPhysicalDeviceMemoryProperties* pMemoryProperties) const;
virtual PFN_vkVoidFunction	getDeviceProcAddr									(VkDevice device, const char* pName) const;
virtual VkResult			createDevice										(VkPhysicalDevice physicalDevice, const VkDeviceCreateInfo* pCreateInfo, const VkAllocationCallbacks* pAllocator, VkDevice* pDevice) const;
virtual VkResult			enumerateDeviceExtensionProperties					(VkPhysicalDevice physicalDevice, const char* pLayerName, deUint32* pPropertyCount, VkExtensionProperties* pProperties) const;
virtual VkResult			enumerateDeviceLayerProperties						(VkPhysicalDevice physicalDevice, deUint32* pPropertyCount, VkLayerProperties* pProperties) const;
virtual void				getPhysicalDeviceSparseImageFormatProperties		(VkPhysicalDevice physicalDevice, VkFormat format, VkImageType type, VkSampleCountFlagBits samples, VkImageUsageFlags usage, VkImageTiling tiling, deUint32* pPropertyCount, VkSparseImageFormatProperties* pProperties) const;
virtual void				destroySurfaceKHR									(VkInstance instance, VkSurfaceKHR surface, const VkAllocationCallbacks* pAllocator) const;
virtual VkResult			getPhysicalDeviceSurfaceSupportKHR					(VkPhysicalDevice physicalDevice, deUint32 queueFamilyIndex, VkSurfaceKHR surface, VkBool32* pSupported) const;
virtual VkResult			getPhysicalDeviceSurfaceCapabilitiesKHR				(VkPhysicalDevice physicalDevice, VkSurfaceKHR surface, VkSurfaceCapabilitiesKHR* pSurfaceCapabilities) const;
virtual VkResult			getPhysicalDeviceSurfaceFormatsKHR					(VkPhysicalDevice physicalDevice, VkSurfaceKHR surface, deUint32* pSurfaceFormatCount, VkSurfaceFormatKHR* pSurfaceFormats) const;
virtual VkResult			getPhysicalDeviceSurfacePresentModesKHR				(VkPhysicalDevice physicalDevice, VkSurfaceKHR surface, deUint32* pPresentModeCount, VkPresentModeKHR* pPresentModes) const;
virtual VkResult			getPhysicalDeviceDisplayPropertiesKHR				(VkPhysicalDevice physicalDevice, deUint32* pPropertyCount, VkDisplayPropertiesKHR* pProperties) const;
virtual VkResult			getPhysicalDeviceDisplayPlanePropertiesKHR			(VkPhysicalDevice physicalDevice, deUint32* pPropertyCount, VkDisplayPlanePropertiesKHR* pProperties) const;
virtual VkResult			getDisplayPlaneSupportedDisplaysKHR					(VkPhysicalDevice physicalDevice, deUint32 planeIndex, deUint32* pDisplayCount, VkDisplayKHR* pDisplays) const;
virtual VkResult			getDisplayModePropertiesKHR							(VkPhysicalDevice physicalDevice, VkDisplayKHR display, deUint32* pPropertyCount, VkDisplayModePropertiesKHR* pProperties) const;
virtual VkResult			createDisplayModeKHR								(VkPhysicalDevice physicalDevice, VkDisplayKHR display, const VkDisplayModeCreateInfoKHR* pCreateInfo, const VkAllocationCallbacks* pAllocator, VkDisplayModeKHR* pMode) const;
virtual VkResult			getDisplayPlaneCapabilitiesKHR						(VkPhysicalDevice physicalDevice, VkDisplayModeKHR mode, deUint32 planeIndex, VkDisplayPlaneCapabilitiesKHR* pCapabilities) const;
virtual VkResult			createDisplayPlaneSurfaceKHR						(VkInstance instance, const VkDisplaySurfaceCreateInfoKHR* pCreateInfo, const VkAllocationCallbacks* pAllocator, VkSurfaceKHR* pSurface) const;
virtual VkResult			createXlibSurfaceKHR								(VkInstance instance, const VkXlibSurfaceCreateInfoKHR* pCreateInfo, const VkAllocationCallbacks* pAllocator, VkSurfaceKHR* pSurface) const;
virtual VkBool32			getPhysicalDeviceXlibPresentationSupportKHR			(VkPhysicalDevice physicalDevice, deUint32 queueFamilyIndex, pt::XlibDisplayPtr dpy, pt::XlibVisualID visualID) const;
virtual VkResult			createXcbSurfaceKHR									(VkInstance instance, const VkXcbSurfaceCreateInfoKHR* pCreateInfo, const VkAllocationCallbacks* pAllocator, VkSurfaceKHR* pSurface) const;
virtual VkBool32			getPhysicalDeviceXcbPresentationSupportKHR			(VkPhysicalDevice physicalDevice, deUint32 queueFamilyIndex, pt::XcbConnectionPtr connection, pt::XcbVisualid visual_id) const;
virtual VkResult			createWaylandSurfaceKHR								(VkInstance instance, const VkWaylandSurfaceCreateInfoKHR* pCreateInfo, const VkAllocationCallbacks* pAllocator, VkSurfaceKHR* pSurface) const;
virtual VkBool32			getPhysicalDeviceWaylandPresentationSupportKHR		(VkPhysicalDevice physicalDevice, deUint32 queueFamilyIndex, pt::WaylandDisplayPtr display) const;
virtual VkResult			createMirSurfaceKHR									(VkInstance instance, const VkMirSurfaceCreateInfoKHR* pCreateInfo, const VkAllocationCallbacks* pAllocator, VkSurfaceKHR* pSurface) const;
virtual VkBool32			getPhysicalDeviceMirPresentationSupportKHR			(VkPhysicalDevice physicalDevice, deUint32 queueFamilyIndex, pt::MirConnectionPtr connection) const;
virtual VkResult			createAndroidSurfaceKHR								(VkInstance instance, const VkAndroidSurfaceCreateInfoKHR* pCreateInfo, const VkAllocationCallbacks* pAllocator, VkSurfaceKHR* pSurface) const;
virtual VkResult			createWin32SurfaceKHR								(VkInstance instance, const VkWin32SurfaceCreateInfoKHR* pCreateInfo, const VkAllocationCallbacks* pAllocator, VkSurfaceKHR* pSurface) const;
virtual VkBool32			getPhysicalDeviceWin32PresentationSupportKHR		(VkPhysicalDevice physicalDevice, deUint32 queueFamilyIndex) const;
virtual void				getPhysicalDeviceFeatures2KHR						(VkPhysicalDevice physicalDevice, VkPhysicalDeviceFeatures2KHR* pFeatures) const;
virtual void				getPhysicalDeviceProperties2KHR						(VkPhysicalDevice physicalDevice, VkPhysicalDeviceProperties2KHR* pProperties) const;
virtual void				getPhysicalDeviceFormatProperties2KHR				(VkPhysicalDevice physicalDevice, VkFormat format, VkFormatProperties2KHR* pFormatProperties) const;
virtual VkResult			getPhysicalDeviceImageFormatProperties2KHR			(VkPhysicalDevice physicalDevice, const VkPhysicalDeviceImageFormatInfo2KHR* pImageFormatInfo, VkImageFormatProperties2KHR* pImageFormatProperties) const;
virtual void				getPhysicalDeviceQueueFamilyProperties2KHR			(VkPhysicalDevice physicalDevice, deUint32* pQueueFamilyPropertyCount, VkQueueFamilyProperties2KHR* pQueueFamilyProperties) const;
virtual void				getPhysicalDeviceMemoryProperties2KHR				(VkPhysicalDevice physicalDevice, VkPhysicalDeviceMemoryProperties2KHR* pMemoryProperties) const;
virtual void				getPhysicalDeviceSparseImageFormatProperties2KHR	(VkPhysicalDevice physicalDevice, const VkPhysicalDeviceSparseImageFormatInfo2KHR* pFormatInfo, deUint32* pPropertyCount, VkSparseImageFormatProperties2KHR* pProperties) const;
virtual VkResult			getPhysicalDeviceSurfaceCapabilities2KHR			(VkPhysicalDevice physicalDevice, const VkPhysicalDeviceSurfaceInfo2KHR* pSurfaceInfo, VkSurfaceCapabilities2KHR* pSurfaceCapabilities) const;
virtual VkResult			getPhysicalDeviceSurfaceFormats2KHR					(VkPhysicalDevice physicalDevice, const VkPhysicalDeviceSurfaceInfo2KHR* pSurfaceInfo, deUint32* pSurfaceFormatCount, VkSurfaceFormat2KHR* pSurfaceFormats) const;
virtual VkResult			createDebugReportCallbackEXT						(VkInstance instance, const VkDebugReportCallbackCreateInfoEXT* pCreateInfo, const VkAllocationCallbacks* pAllocator, VkDebugReportCallbackEXT* pCallback) const;
virtual void				destroyDebugReportCallbackEXT						(VkInstance instance, VkDebugReportCallbackEXT callback, const VkAllocationCallbacks* pAllocator) const;
virtual void				debugReportMessageEXT								(VkInstance instance, VkDebugReportFlagsEXT flags, VkDebugReportObjectTypeEXT objectType, deUint64 object, deUintptr location, deInt32 messageCode, const char* pLayerPrefix, const char* pMessage) const;
virtual VkResult			getPhysicalDeviceExternalImageFormatPropertiesNV	(VkPhysicalDevice physicalDevice, VkFormat format, VkImageType type, VkImageTiling tiling, VkImageUsageFlags usage, VkImageCreateFlags flags, VkExternalMemoryHandleTypeFlagsNV externalHandleType, VkExternalImageFormatPropertiesNV* pExternalImageFormatProperties) const;
